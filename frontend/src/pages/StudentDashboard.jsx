import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { fetchStudent } from '../api/students';
import { fetchApplications } from '../api/applications';
import { fetchInterviews } from '../api/interviews';
import { fetchPlacements } from '../api/placements';
import { fetchJobNotifications } from '../api/jobNotifications';
import { useAuth } from '../context/AuthContext';
import WorkflowTracker from '../components/WorkflowTracker';

export default function StudentDashboard() {
  const { refId, username } = useAuth();
  const [student, setStudent] = useState(null);
  const [applications, setApplications] = useState([]);
  const [interviews, setInterviews] = useState([]);
  const [placements, setPlacements] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelled = false;
    async function load() {
      setLoading(true);
      setError('');
      try {
        const [studentData, apps, interviewRows, placementRows, notifRows] = await Promise.all([
          refId ? fetchStudent(refId) : Promise.resolve(null),
          fetchApplications(),
          fetchInterviews(),
          fetchPlacements(),
          fetchJobNotifications(),
        ]);
        if (cancelled) return;
        setStudent(studentData);
        const myApps = (Array.isArray(apps) ? apps : []).filter((a) => a.student?.student_id === refId);
        setApplications(myApps);
        const myAppIds = new Set(myApps.map((a) => a.application_id));
        setInterviews((Array.isArray(interviewRows) ? interviewRows : []).filter((i) => myAppIds.has(i.application?.application_id)));
        setPlacements((Array.isArray(placementRows) ? placementRows : []).filter((p) => myAppIds.has(p.application?.application_id)));
        setNotifications(
          (Array.isArray(notifRows) ? notifRows : [])
            .slice()
            .sort((a, b) => (b.notification_id || 0) - (a.notification_id || 0))
            .slice(0, 6)
        );
      } catch (err) {
        if (!cancelled) setError(err.message || 'Could not load your dashboard.');
      } finally {
        if (!cancelled) setLoading(false);
      }
    }
    load();
    return () => {
      cancelled = true;
    };
  }, [refId]);

  const selectedCount = placements.filter((p) => p.selection_status === 'Selected').length;

  const pipelineStages = [
    { key: 'notifications', label: 'Notification released', count: loading ? null : notifications.length },
    { key: 'applications', label: 'You applied', count: loading ? null : applications.length },
    { key: 'interviews', label: 'Interview scheduled', count: loading ? null : interviews.length },
    { key: 'placements', label: 'Result published', count: loading ? null : placements.length },
  ];

  return (
    <div>
      <div className="page-header">
        <div>
          <span className="page-eyebrow">Student · Overview</span>
          <h1>Welcome{student?.name ? `, ${student.name}` : `, ${username}`}</h1>
          <p>Track your applications, interviews and the latest hiring requirements in one place.</p>
        </div>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      <div className="stat-row">
        <div className="stat-card">
          <div className="num">{loading ? '…' : applications.length}</div>
          <div className="lbl">Applications</div>
        </div>
        <div className="stat-card">
          <div className="num">{loading ? '…' : interviews.length}</div>
          <div className="lbl">Interviews</div>
        </div>
        <div className="stat-card">
          <div className="num">{loading ? '…' : selectedCount}</div>
          <div className="lbl">Offers</div>
        </div>
        <div className="stat-card">
          <div className="num">{loading ? '…' : notifications.length}</div>
          <div className="lbl">Recent postings</div>
        </div>
      </div>

      <WorkflowTracker
        eyebrow="Your journey · live"
        title="Your path from posting to placement"
        stages={pipelineStages}
      />

      <div className="dash-grid">
        <div className="panel">
          <div className="panel-title">
            <h3>Your profile</h3>
            <Link className="btn btn-ghost" to="/students">
              Edit in Students
            </Link>
          </div>
          {student ? (
            <div className="profile-grid">
              <div className="profile-field">
                <div className="k">Branch</div>
                <div className="v">{student.branch || '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">CGPA</div>
                <div className="v">{student.cgpa ?? '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">Graduation year</div>
                <div className="v">{student.graduation_year ?? '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">College</div>
                <div className="v">{student.college?.cname || '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">Email</div>
                <div className="v">{student.email || '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">Skills</div>
                <div className="v">{student.skills || '—'}</div>
              </div>
            </div>
          ) : (
            <div className="empty-state">{loading ? 'Loading…' : 'Profile not found.'}</div>
          )}
        </div>

        <div className="panel">
          <div className="panel-title">
            <h3>Latest hiring requirements</h3>
            <Link className="btn btn-ghost" to="/notifications">
              View all
            </Link>
          </div>
          {notifications.length === 0 && <div className="empty-state">Nothing posted yet.</div>}
          {notifications.map((n) => (
            <div className="list-row" key={n.notification_id}>
              <div className="list-row-main">
                <div className="list-row-title">{n.title}</div>
                <div className="list-row-sub">{n.publish_date}</div>
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="panel">
        <div className="panel-title">
          <h3>Your applications</h3>
          <Link className="btn btn-ghost" to="/applications">
            Apply / manage
          </Link>
        </div>
        {applications.length === 0 && <div className="empty-state">You haven't applied to anything yet.</div>}
        {applications.map((a) => (
          <div className="list-row" key={a.application_id}>
            <div className="list-row-main">
              <div className="list-row-title">
                {a.hiringRequirement?.jobRole || 'Role'} @ {a.hiringRequirement?.company?.company_name || '—'}
              </div>
              <div className="list-row-sub">Applied {a.applied_date || '—'}</div>
            </div>
            <span className="badge badge-brass">{a.application_status || 'Applied'}</span>
          </div>
        ))}
      </div>
    </div>
  );
}
