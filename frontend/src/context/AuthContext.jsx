import { createContext, useContext, useEffect, useState } from 'react';
import { getToken, setToken, clearToken } from '../api/client';

const AuthContext = createContext(null);

const USERNAME_KEY = 'crs_username';
const ROLE_KEY = 'crs_role';
const REFID_KEY = 'crs_refid';

export function AuthProvider({ children }) {
  const [token, setTokenState] = useState(getToken());
  const [username, setUsernameState] = useState(localStorage.getItem(USERNAME_KEY));
  const [role, setRoleState] = useState(localStorage.getItem(ROLE_KEY));
  const [refId, setRefIdState] = useState(() => {
    const raw = localStorage.getItem(REFID_KEY);
    return raw ? Number(raw) : null;
  });

  useEffect(() => {
    // Keep other tabs / components in sync if the token is cleared elsewhere
    // (e.g. a 401 response from the API client).
    const interval = setInterval(() => {
      const current = getToken();
      if (current !== token) setTokenState(current);
    }, 1000);
    return () => clearInterval(interval);
  }, [token]);

  // login({ token, username, role, refId })
  const login = ({ token: jwt, username: uname, role: r, refId: rid }) => {
    setToken(jwt);
    localStorage.setItem(USERNAME_KEY, uname || '');
    localStorage.setItem(ROLE_KEY, r || '');
    if (rid !== undefined && rid !== null) {
      localStorage.setItem(REFID_KEY, String(rid));
    } else {
      localStorage.removeItem(REFID_KEY);
    }
    setTokenState(jwt);
    setUsernameState(uname);
    setRoleState(r);
    setRefIdState(rid ?? null);
  };

  const logout = () => {
    clearToken();
    localStorage.removeItem(USERNAME_KEY);
    localStorage.removeItem(ROLE_KEY);
    localStorage.removeItem(REFID_KEY);
    setTokenState(null);
    setUsernameState(null);
    setRoleState(null);
    setRefIdState(null);
  };

  return (
    <AuthContext.Provider
      value={{ token, username, role, refId, isAuthenticated: !!token, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within an AuthProvider');
  return ctx;
}
