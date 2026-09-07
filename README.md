# Campus Recruitment System — Full Stack

A robust, full-stack campus recruitment and placement management platform built with Spring Boot and React.

```
campus-recruitment-system/
├── backend/    Spring Boot REST API (Java 21, Maven)
└── frontend/   React application, talks to the API via JWT bearer tokens
```

---

## 1. Run the Backend

1. **Database Setup**:
   Create a MySQL database (or configure your cloud MySQL database):
   ```sql
   CREATE DATABASE campus_hiring;
   ```
2. Check `backend/src/main/resources/application.properties` and verify DB connection properties (`spring.datasource.url`, `username`, `password`).
3. Run with Maven wrapper from `backend/`:
   ```powershell
   ./mvnw.cmd spring-boot:run
   ```
   Or open `backend/` in IntelliJ IDEA / VS Code and run `CollegePlacementManagementApplication.java`.
   The API will be live on **http://localhost:8080**.

---

## 2. Run the Frontend

1. Navigate into `frontend/`:
   ```powershell
   cd frontend
   npm install
   ```
2. Start the dev server:
   ```powershell
   npm start
   # or
   npm run dev
   ```
3. Open **http://localhost:3000** (or port configured by the dev server).
   `frontend/.env` points `REACT_APP_API_BASE_URL` to `http://localhost:8080`.

---

## 3. Key Architecture & Features

### Role-Based Portals & Authentication
- **Role-specific registration**: `/register` provides distinct registration flows for **Student**, **Company**, and **College**.
- **BCrypt password security**: Passwords are encrypted with BCrypt (strength 10).
- **Stateless JWT tokens**: Requests are authenticated via standard `Authorization: Bearer <token>` headers with role-based claims.
- **Dynamic dashboards**:
  - **Student**: Profile snapshot, application tracking, interview schedules, offers, and 1-click job applications.
  - **Company**: Open hiring requirements, student applications received, college participation tracking, and candidate interviews.
  - **College**: Student roster, hosting drives, campus interview tracking, and incoming recruiter requirements.
  - **Admin**: Overall placement analytics, funnel visualization, and directory management.

### Real-Time Job Notifications
- When a company posts a new hiring requirement, a notification announcement is automatically generated and broadcast.
- The interactive bell icon in the navigation bar displays unread notifications and pops up toast alerts for new openings.

---

## 4. Stability & Production Readiness Improvements

The system has been hardened with the following fixes:

1. **Eliminated Controller Runtime Crashes (`ClassCastException`)**:
   Replaced faulty `(ResponseEntity<T>) ResponseEntity.badRequest()` type-casts across all controllers with proper REST status builders.
2. **Normalized Database Relations**:
   - Fixed `Application` -> `Student` relationship from `@OneToOne` to `@ManyToOne`, enabling students to apply for multiple job openings across companies.
   - Fixed `HiringRequirement` -> `Company` relationship from `@OneToOne` to `@ManyToOne`, allowing companies to post multiple positions.
   - Fixed `Job_Notification` -> `HiringRequirement` from `@OneToOne` to `@ManyToOne`.
   - Added missing `College` relationship to `College_Participation` to link colleges with drives.
3. **Decimal CGPA Precision**:
   Migrated CGPA and minimum CGPA fields from `Integer`/`Long` to `Double` to support real-world floating-point GPAs (e.g. 8.75, 3.8).
4. **Deleted Bug in `Job_nService`**:
   Fixed notification deletion to call `jobNRepository.deleteById(id)`.
5. **Centralized `@RestControllerAdvice` Exception Handling**:
   Implemented `GlobalExceptionHandler` and `ResourceNotFoundException` to return consistent JSON error envelopes (`{ timestamp, status, error, message }`).
6. **Robust JWT & Stateless Security**:
   Hardened `JwtFilter` with try-catch blocks to prevent 500 errors on expired/malformed tokens, properly populated `GrantedAuthority` roles, and configured stateless session policy.
7. **Form Empty-String Sanitization**:
   Updated `RecordForm.jsx` to map empty optional numeric and date fields to `null` before sending, preventing Jackson deserialization errors.
8. **Build & Tooling Compatibility**:
   Added `.mvn/maven.config` with resolver security flags for uninterrupted Maven builds and added `"dev": "react-scripts start"` to `package.json`.
