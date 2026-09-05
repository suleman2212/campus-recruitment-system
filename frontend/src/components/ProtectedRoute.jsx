import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import Sidebar from './Sidebar';
import NotificationBell from './NotificationBell';
import PageMotion from './PageMotion';

// `allow`, if given, is a list of roles permitted on this route
// (e.g. allow={['COMPANY']}). Signed-in users with any other role
// get bounced back to their own dashboard instead of seeing a 403.
export default function ProtectedRoute({ children, allow }) {
  const { isAuthenticated, role } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (allow && allow.length > 0 && role && !allow.includes(role)) {
    return <Navigate to="/" replace />;
  }

  return (
    <div className="app-shell">
      <Sidebar />
      <div className="main-col">
        <div className="topbar">
          <span className="topbar-title">{role ? `${role.charAt(0)}${role.slice(1).toLowerCase()} console` : 'Console'}</span>
          <NotificationBell />
        </div>
        <main className="main">
          <PageMotion>{children}</PageMotion>
        </main>
      </div>
    </div>
  );
}
