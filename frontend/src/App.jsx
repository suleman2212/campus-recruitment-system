import { Route, Routes } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import Login from './pages/Login';
import RegisterChoice from './pages/RegisterChoice';
import RegisterStudent from './pages/RegisterStudent';
import RegisterCompany from './pages/RegisterCompany';
import RegisterCollege from './pages/RegisterCollege';
import Dashboard from './pages/Dashboard';
import Colleges from './pages/Colleges';
import Students from './pages/Students';
import Companies from './pages/Companies';
import HiringRequirements from './pages/HiringRequirements';
import JobNotifications from './pages/JobNotifications';
import Applications from './pages/Applications';
import CollegeParticipation from './pages/CollegeParticipation';
import HostColleges from './pages/HostColleges';
import Interviews from './pages/Interviews';
import Placements from './pages/Placements';

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<RegisterChoice />} />
      <Route path="/register/student" element={<RegisterStudent />} />
      <Route path="/register/company" element={<RegisterCompany />} />
      <Route path="/register/college" element={<RegisterCollege />} />

      <Route
        path="/"
        element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        }
      />
      <Route
        path="/colleges"
        element={
          <ProtectedRoute>
            <Colleges />
          </ProtectedRoute>
        }
      />
      <Route
        path="/students"
        element={
          <ProtectedRoute>
            <Students />
          </ProtectedRoute>
        }
      />
      <Route
        path="/companies"
        element={
          <ProtectedRoute>
            <Companies />
          </ProtectedRoute>
        }
      />
      <Route
        path="/requirements"
        element={
          <ProtectedRoute>
            <HiringRequirements />
          </ProtectedRoute>
        }
      />
      <Route
        path="/notifications"
        element={
          <ProtectedRoute>
            <JobNotifications />
          </ProtectedRoute>
        }
      />
      <Route
        path="/applications"
        element={
          <ProtectedRoute>
            <Applications />
          </ProtectedRoute>
        }
      />
      <Route
        path="/participation"
        element={
          <ProtectedRoute>
            <CollegeParticipation />
          </ProtectedRoute>
        }
      />
      <Route
        path="/host-colleges"
        element={
          <ProtectedRoute>
            <HostColleges />
          </ProtectedRoute>
        }
      />
      <Route
        path="/interviews"
        element={
          <ProtectedRoute>
            <Interviews />
          </ProtectedRoute>
        }
      />
      <Route
        path="/placements"
        element={
          <ProtectedRoute>
            <Placements />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}
