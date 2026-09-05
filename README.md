# Campus Recruitment System — Full Stack

Two projects, meant to be opened as **two separate IntelliJ IDEA projects/windows**:

```
campus-recruitment-system/
├── backend/    Spring Boot 4 REST API (Java 21, Maven)
└── frontend/   React + Vite app (.jsx), talks to the API with fetch()
```

## 1. Run the backend

1. Create the MySQL database first: `CREATE DATABASE campus_hiring;`
2. Open `backend/` in **IntelliJ IDEA 2026.1** as a Maven project (File → Open → select the `backend` folder — IntelliJ will auto-import `pom.xml`).
3. Check `backend/src/main/resources/application.properties` and update the DB URL/username/password to match your local MySQL instance.
4. Run `CollegePlacementManagementApplication.java` (right-click → Run). The API starts on **http://localhost:8080**.

## 2. Run the frontend

1. Open `frontend/` as its own project/folder in IntelliJ (or any editor).
2. In a terminal inside `frontend/`:
   ```
   npm install
   npm run dev
   ```
3. Open **http://localhost:5173**. `frontend/.env` points `VITE_API_BASE_URL` at `http://localhost:8080` — change it if your backend runs elsewhere.

## 3. What's new in this version

### Separate registration for each account type
The old single "sign up" page is gone. `/register` now shows a picker for **Student**, **Company**, or **College**, each with its own form (`/register/student`, `/register/company`, `/register/college`). Registering creates the login *and* the matching profile row (Student/Company/College) in one step, and links them via a `role` + `refId` on the `Users` table.

- Students pick their college from a dropdown (populated from whichever colleges have already registered) — so register at least one **College** account before any **Student** accounts.
- Companies and Colleges register standalone.

### Role-based dashboards
After login you land on `/`, which now renders a different dashboard depending on your account's role:
- **Student** — profile snapshot, application list + status, interview count, latest hiring requirements.
- **Company** — profile, posted hiring requirements, applications received, host colleges.
- **College** — profile, student directory, hosted drives, latest hiring requirements.
- Any legacy/unrecognized account falls back to the original admin-style overview (`AdminDashboard.jsx`).

The left sidebar navigation also changes per role, showing only the sections relevant to that account.

### Hiring-requirement notifications with a pop-up
When a company submits `POST /hiringrequirement/insert/{cid}`, the backend (`HiringRService`) now **automatically creates a `Job_Notification`** describing the new opening. The bell icon in the top bar (visible on every page once logged in) polls for notifications every 20s:
- New notifications trigger a **toast pop-up** in the corner (auto-dismisses after ~8s, or click × to close early).
- The bell shows an unread badge; clicking it opens a dropdown with the recent notification list and marks them read.

### Login fixes
The original `UserService.verify()` issued a JWT for any submitted username **without actually checking the password** — this has been fixed (`AuthService.login`) with proper BCrypt verification. `POST /users/login` now returns JSON (`{ token, username, role, refId }`) instead of a raw token string, and a bad login returns a real "Invalid username or password" message instead of the frontend showing a generic "session expired" error.

## 4. Using it end-to-end

1. Go to `/register`, choose **College**, and register at least one college.
2. Register a **Student** (they'll pick that college from the dropdown) and/or a **Company**.
3. Log in as the **Company** account → dashboard → "Post a hiring requirement" → fill out the Hiring Requirements form. This fires a notification.
4. Log in as the **Student** or **College** account (or just watch the bell while still on that tab) — within 20s a pop-up appears announcing the new requirement.
5. As a Student, go to Applications and apply to the posted requirement; as a Company, track it from Applications/Interviews/Placements.

## Notes on the backend as originally provided

Pre-existing bugs fixed so the app works end-to-end:
- `pom.xml` / `application.properties` — resolved merge-conflict markers (wouldn't compile otherwise).
- `StrudentController` — `/student/insert` was missing the `{cid}` path variable the service required.
- `Job_nController` — the update endpoint was missing `@RequestBody`, so edits were silently ignored.
- `CorsConfig.java` added and `.cors()` enabled in `SecurityConfig` for cross-origin `fetch()` calls from the React dev server.
- `UserService.verify()` didn't check the password at all — fixed in `AuthService.login()`.

## A note on scope (read this before a production deploy)

This is still a student/portfolio-scale project, not a hardened multi-tenant system:
- There's no server-side enforcement that a Student can only edit *their own* application, or a Company only *their own* requirements — the CRUD pages (`/students`, `/applications`, etc.) are reachable by any authenticated role and operate on the full table. The dashboards and sidebar nav steer people toward their own data, but a determined user could still open, say, `/students` as a Company account and see everyone.
- Notifications are broadcast to everyone (no per-college/per-branch targeting), matching the simple entity model already in place.
- If you need real per-role authorization, the next step would be adding `@PreAuthorize` checks (or manual ownership checks) in each Controller based on the `role`/`refId` claims already embedded in the JWT.
