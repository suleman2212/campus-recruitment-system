import { apiGet, apiPost } from './client';

// POST /users/register/student -> { message, username, role, refId }
export const registerStudent = (payload) => apiPost('/users/register/student', payload);

// POST /users/register/company -> { message, username, role, refId }
export const registerCompany = (payload) => apiPost('/users/register/company', payload);

// POST /users/register/college -> { message, username, role, refId }
export const registerCollege = (payload) => apiPost('/users/register/college', payload);

// POST /users/login -> { token, username, role, refId }
export function loginUser({ username, password }) {
  return apiPost('/users/login', { username, password });
}

// GET /users/fetch -> Users[]   (requires auth)
export function fetchUsers() {
  return apiGet('/users/fetch');
}
