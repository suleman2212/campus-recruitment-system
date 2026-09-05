import { useAuth } from '../context/AuthContext';
import StudentDashboard from './StudentDashboard';
import CompanyDashboard from './CompanyDashboard';
import CollegeDashboard from './CollegeDashboard';
import AdminDashboard from './AdminDashboard';

// Picks the right dashboard for the signed-in account's role.
export default function Dashboard() {
  const { role } = useAuth();

  if (role === 'STUDENT') return <StudentDashboard />;
  if (role === 'COMPANY') return <CompanyDashboard />;
  if (role === 'COLLEGE') return <CollegeDashboard />;
  return <AdminDashboard />;
}
