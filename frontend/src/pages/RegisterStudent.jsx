import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { registerStudent } from '../api/auth';
import { fetchColleges } from '../api/colleges';

const EMPTY = {
  username: '',
  password: '',
  collegeId: '',
  name: '',
  email: '',
  phone: '',
  branch: '',
  cgpa: '',
  graduationYear: '',
  skills: '',
  linkedin: '',
  github: '',
  resume: '',
};

export default function RegisterStudent() {
  const [values, setValues] = useState(EMPTY);
  const [colleges, setColleges] = useState([]);
  const [collegesError, setCollegesError] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    fetchColleges()
      .then((data) => setColleges(Array.isArray(data) ? data : []))
      .catch(() => setCollegesError('Could not load the college list. Make sure at least one college has registered.'));
  }, []);

  const set = (name) => (e) => setValues((v) => ({ ...v, [name]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);
    try {
      const payload = {
        ...values,
        collegeId: values.collegeId ? Number(values.collegeId) : null,
        phone: values.phone ? Number(values.phone) : null,
        cgpa: values.cgpa ? Number(values.cgpa) : null,
        graduationYear: values.graduationYear ? Number(values.graduationYear) : null,
      };
      await registerStudent(payload);
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
        <h1>Student registration</h1>
        <p className="auth-subtitle">Create your login and build your placement profile.</p>

        {error && <div className="alert alert-error">{error}</div>}
        {collegesError && <div className="alert alert-error">{collegesError}</div>}
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
              <label htmlFor="collegeId">College</label>
              <select id="collegeId" value={values.collegeId} onChange={set('collegeId')} required>
                <option value="">Select your college…</option>
                {colleges.map((c) => (
                  <option key={c.cid} value={c.cid}>
                    {c.cname}
                  </option>
                ))}
              </select>
            </div>
            <div className="field">
              <label htmlFor="name">Full name</label>
              <input id="name" value={values.name} onChange={set('name')} required />
            </div>
            <div className="field">
              <label htmlFor="email">Email</label>
              <input id="email" type="email" value={values.email} onChange={set('email')} required />
            </div>
            <div className="field">
              <label htmlFor="phone">Phone</label>
              <input id="phone" type="number" value={values.phone} onChange={set('phone')} />
            </div>
            <div className="field">
              <label htmlFor="branch">Branch</label>
              <input id="branch" value={values.branch} onChange={set('branch')} placeholder="e.g. Computer Science" />
            </div>
            <div className="field">
              <label htmlFor="cgpa">CGPA</label>
              <input id="cgpa" type="number" step="any" value={values.cgpa} onChange={set('cgpa')} />
            </div>
            <div className="field">
              <label htmlFor="graduationYear">Graduation year</label>
              <input id="graduationYear" type="number" value={values.graduationYear} onChange={set('graduationYear')} />
            </div>
            <div className="field">
              <label htmlFor="skills">Skills</label>
              <input id="skills" value={values.skills} onChange={set('skills')} placeholder="Comma separated" />
            </div>
            <div className="field">
              <label htmlFor="linkedin">LinkedIn</label>
              <input id="linkedin" value={values.linkedin} onChange={set('linkedin')} />
            </div>
            <div className="field">
              <label htmlFor="github">GitHub</label>
              <input id="github" value={values.github} onChange={set('github')} />
            </div>
            <div className="field" style={{ gridColumn: '1 / -1' }}>
              <label htmlFor="resume">Resume link</label>
              <input id="resume" value={values.resume} onChange={set('resume')} />
            </div>
          </div>

          <button className="btn btn-brass" type="submit" style={{ width: '100%' }} disabled={submitting}>
            {submitting ? 'Creating…' : 'Create student account'}
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
