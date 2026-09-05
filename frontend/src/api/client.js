// Thin wrapper around the native fetch() API.
// The backend is a Spring Boot REST API secured with a JWT bearer token
// (see SecurityConfig.java) - every route except /users/login, /users/register
// and /users/jwt requires an "Authorization: Bearer <token>" header.

export const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';

const TOKEN_KEY = 'crs_token';

export function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function setToken(token) {
  if (token) localStorage.setItem(TOKEN_KEY, token);
}

export function clearToken() {
  localStorage.removeItem(TOKEN_KEY);
}

class ApiError extends Error {
  constructor(message, status) {
    super(message);
    this.status = status;
  }
}

/**
 * Generic JSON request helper built on fetch().
 * @param {string} path - API path, e.g. "/student/fetch"
 * @param {object} options
 * @param {'GET'|'POST'|'PUT'|'DELETE'} [options.method]
 * @param {object} [options.body] - will be JSON.stringify-ed
 * @param {boolean} [options.auth] - attach the bearer token (default true)
 */
export async function apiRequest(path, { method = 'GET', body, auth = true } = {}) {
  const headers = { 'Content-Type': 'application/json' };

  if (auth) {
    const token = getToken();
    if (token) headers['Authorization'] = `Bearer ${token}`;
  }

  let response;
  try {
    response = await fetch(`${API_BASE_URL}${path}`, {
      method,
      headers,
      body: body !== undefined ? JSON.stringify(body) : undefined,
    });
  } catch (networkErr) {
    throw new ApiError(
      `Could not reach the server at ${API_BASE_URL}. Is the Spring Boot backend running?`,
      0
    );
  }

  const hadStoredToken = auth && !!getToken();
  const rawText = await response.text();
  const looksLikeJson = rawText.trim().startsWith('{') || rawText.trim().startsWith('[');
  const data = rawText && looksLikeJson ? JSON.parse(rawText) : rawText;

  if (response.status === 401 || response.status === 403) {
    if (hadStoredToken) {
      // We *were* sending a bearer token and it was rejected -> force re-login.
      clearToken();
      throw new ApiError('Your session has expired. Please log in again.', response.status);
    }
    // No token was sent (e.g. a login/registration attempt) - surface the
    // server's actual message ("Invalid username or password.", etc).
    const message = (data && data.message) || rawText || 'Invalid username or password.';
    throw new ApiError(message, response.status);
  }

  if (!response.ok) {
    const message = (data && data.message) || rawText || `Request failed (HTTP ${response.status})`;
    throw new ApiError(message, response.status);
  }

  return data;
}

export const apiGet = (path) => apiRequest(path, { method: 'GET' });
export const apiPost = (path, body) => apiRequest(path, { method: 'POST', body });
export const apiPut = (path, body) => apiRequest(path, { method: 'PUT', body });
export const apiDelete = (path) => apiRequest(path, { method: 'DELETE' });

export { ApiError };
