import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { registerCompany } from '../api/auth';

const EMPTY = {
  username: '',
  password: '',
  companyName: '',
  location: '',
  email: '',
  website: '',
  phone: '',
};

export default function RegisterCompany() {
  const [values, setValues] = useState(EMPTY);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const navigate = useNavigate();

  const set = (name) => (e) => setValues((v) => ({ ...v, [name]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);
    try {
      const payload = { ...values, phone: values.phone ? Number(values.phone) : null };
      await registerCompany(payload);
      setSuccess(true);
      setTimeout(() => navigate('/login'), 900);
    } catch (err) {
      setError(err.message || 'Registration failed.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="auth-shell">
      <div className="auth-card auth-card-wide">
        <span className="auth-mark">Registrar Console</span>
        <h1>Company registration</h1>
        <p className="auth-subtitle">Create your login and set up your recruiter profile.</p>

        {error && <div className="alert alert-error">{error}</div>}
        {success && <div className="alert alert-success">Account created — redirecting to sign in…</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-grid">
            <div className="field">
              <label htmlFor="username">Username</label>
              <input id="username" value={values.username} onChange={set('username')} required autoFocus />
            </div>
            <div className="field">
              <label htmlFor="password">Password</label>
              <input id="password" type="password" value={values.password} onChange={set('password')} required />
            </div>
            <div className="field">
              <label htmlFor="companyName">Company name</label>
              <input id="companyName" value={values.companyName} onChange={set('companyName')} required />
            </div>
            <div className="field">
              <label htmlFor="location">Location</label>
              <input id="location" value={values.location} onChange={set('location')} />
            </div>
            <div className="field">
              <label htmlFor="email">Email</label>
              <input id="email" type="email" value={values.email} onChange={set('email')} required />
            </div>
            <div className="field">
              <label htmlFor="website">Website</label>
              <input id="website" value={values.website} onChange={set('website')} />
            </div>
            <div className="field">
              <label htmlFor="phone">Phone</label>
              <input id="phone" type="number" value={values.phone} onChange={set('phone')} />
            </div>
          </div>

          <button className="btn btn-brass" type="submit" style={{ width: '100%' }} disabled={submitting}>
            {submitting ? 'Creating…' : 'Create company account'}
          </button>
        </form>

        <div className="auth-switch">
          <Link to="/register">← Choose a different account type</Link> · Already registered?{' '}
          <Link to="/login">Sign in</Link>
        </div>
      </div>
    </div>
  );
}
