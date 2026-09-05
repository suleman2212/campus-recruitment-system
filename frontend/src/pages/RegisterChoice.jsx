import { Link } from 'react-router-dom';

const ROLES = [
  {
    to: '/register/student',
    tag: 'Student',
    title: 'Register as a Student',
    body: 'Build your profile, browse hiring requirements and apply to companies visiting your college.',
  },
  {
    to: '/register/company',
    tag: 'Company',
    title: 'Register as a Company',
    body: 'Post hiring requirements, review applications and schedule interviews across host colleges.',
  },
  {
    to: '/register/college',
    tag: 'College',
    title: 'Register as a College',
    body: 'Manage your student directory, track placement participation and host companies on campus.',
  },
];

export default function RegisterChoice() {
  return (
    <div className="auth-shell">
      <div className="auth-card auth-card-wide">
        <span className="auth-mark">Registrar Console</span>
        <h1>Create an account</h1>
        <p className="auth-subtitle">Choose the kind of account you need. Each has its own registration form.</p>

        <div className="role-grid">
          {ROLES.map((r) => (
            <Link className="role-card" to={r.to} key={r.to}>
              <span className="role-tag">{r.tag}</span>
              <h3>{r.title}</h3>
              <p>{r.body}</p>
              <span className="role-cta">Continue →</span>
            </Link>
          ))}
        </div>

        <div className="auth-switch">
          Already registered? <Link to="/login">Sign in</Link>
        </div>
      </div>
    </div>
  );
}
