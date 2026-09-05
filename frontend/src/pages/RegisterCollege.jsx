import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { registerCollege } from '../api/auth';

const EMPTY = {
  username: '',
  password: '',
  cname: '',
  region: '',
  address: '',
  pofficer: '',
  pemail: '',
  pnumber: '',
  infraScore: '',
  studentStrength: '',
};

export default function RegisterCollege() {
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
      const payload = {
        ...values,
        pnumber: values.pnumber ? Number(values.pnumber) : null,
        studentStrength: values.studentStrength ? Number(values.studentStrength) : null,
      };
      await registerCollege(payload);
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
        <h1>College registration</h1>
        <p className="auth-subtitle">Create your login and register your institution.</p>

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
              <label htmlFor="cname">College name</label>
              <input id="cname" value={values.cname} onChange={set('cname')} required />
            </div>
            <div className="field">
              <label htmlFor="region">Region</label>
              <input id="region" value={values.region} onChange={set('region')} />
            </div>
            <div className="field" style={{ gridColumn: '1 / -1' }}>
              <label htmlFor="address">Address</label>
              <input id="address" value={values.address} onChange={set('address')} />
            </div>
            <div className="field">
              <label htmlFor="pofficer">Placement officer</label>
              <input id="pofficer" value={values.pofficer} onChange={set('pofficer')} />
            </div>
            <div className="field">
              <label htmlFor="pemail">Officer email</label>
              <input id="pemail" type="email" value={values.pemail} onChange={set('pemail')} />
            </div>
            <div className="field">
              <label htmlFor="pnumber">Officer phone</label>
              <input id="pnumber" type="number" value={values.pnumber} onChange={set('pnumber')} />
            </div>
            <div className="field">
              <label htmlFor="infraScore">Infrastructure score</label>
              <input id="infraScore" value={values.infraScore} onChange={set('infraScore')} />
            </div>
            <div className="field">
              <label htmlFor="studentStrength">Student strength</label>
              <input id="studentStrength" type="number" value={values.studentStrength} onChange={set('studentStrength')} />
            </div>
          </div>

          <button className="btn btn-brass" type="submit" style={{ width: '100%' }} disabled={submitting}>
            {submitting ? 'Creating…' : 'Create college account'}
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
