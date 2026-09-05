import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { fetchCollege } from '../api/colleges';
import { fetchStudents } from '../api/students';
import { fetchHostColleges } from '../api/hostColleges';
import { fetchJobNotifications } from '../api/jobNotifications';
import { fetchParticipations } from '../api/collegeParticipation';
import { fetchInterviews } from '../api/interviews';
import { fetchPlacements } from '../api/placements';
import { useAuth } from '../context/AuthContext';
import WorkflowTracker from '../components/WorkflowTracker';

export default function CollegeDashboard() {
  const { refId, username } = useAuth();
  const [college, setCollege] = useState(null);
  const [students, setStudents] = useState([]);
  const [hostings, setHostings] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [participations, setParticipations] = useState([]);
  const [interviews, setInterviews] = useState([]);
  const [placements, setPlacements] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelled = false;
    async function load() {
      setLoading(true);
      setError('');
      try {
        const [collegeData, studentRows, hostRows, notifRows, participationRows, interviewRows, placementRows] = await Promise.all([
          refId ? fetchCollege(refId) : Promise.resolve(null),
          fetchStudents(),
          fetchHostColleges(),
          fetchJobNotifications(),
          fetchParticipations(),
          fetchInterviews(),
          fetchPlacements(),
        ]);
        if (cancelled) return;
        setCollege(collegeData);
        const myStudents = (Array.isArray(studentRows) ? studentRows : []).filter((s) => s.college?.cid === refId);
        setStudents(myStudents);
        setHostings((Array.isArray(hostRows) ? hostRows : []).filter((h) => h.college?.cid === refId));
        setNotifications(
          (Array.isArray(notifRows) ? notifRows : [])
            .slice()
            .sort((a, b) => (b.notification_id || 0) - (a.notification_id || 0))
            .slice(0, 6)
        );
        setParticipations((Array.isArray(participationRows) ? participationRows : []).filter((p) => p.college?.cid === refId));
        const myStudentIds = new Set(myStudents.map((s) => s.student_id));
        setInterviews((Array.isArray(interviewRows) ? interviewRows : []).filter((i) => myStudentIds.has(i.application?.student?.student_id)));
        setPlacements((Array.isArray(placementRows) ? placementRows : []).filter((p) => myStudentIds.has(p.application?.student?.student_id)));
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

  const pipelineStages = [
    { key: 'notifications', label: 'Notification released', count: loading ? null : notifications.length },
    { key: 'participation', label: 'Grouped for a drive', count: loading ? null : participations.length },
    { key: 'hostColleges', label: 'Hosting confirmed', count: loading ? null : hostings.length },
    { key: 'students', label: 'Students registered', count: loading ? null : students.length },
    { key: 'interviews', label: 'Interviews run', count: loading ? null : interviews.length },
    { key: 'placements', label: 'Results published', count: loading ? null : placements.length },
  ];

  return (
    <div>
      <div className="page-header">
        <div>
          <span className="page-eyebrow">College · Overview</span>
          <h1>Welcome{college?.cname ? `, ${college.cname}` : `, ${username}`}</h1>
          <p>Track your students, hosted drives and incoming hiring requirements.</p>
        </div>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      <div className="stat-row">
        <div className="stat-card">
          <div className="num">{loading ? '…' : students.length}</div>
          <div className="lbl">Students</div>
        </div>
        <div className="stat-card">
          <div className="num">{loading ? '…' : hostings.length}</div>
          <div className="lbl">Hosted drives</div>
        </div>
        <div className="stat-card">
          <div className="num">{loading ? '…' : notifications.length}</div>
          <div className="lbl">Recent postings</div>
        </div>
      </div>

      <WorkflowTracker
        eyebrow="Your campus · live"
        title="Where your students and drives stand"
        stages={pipelineStages}
      />

      <div className="dash-grid">
        <div className="panel">
          <div className="panel-title">
            <h3>College profile</h3>
            <Link className="btn btn-ghost" to="/colleges">
              Edit in Colleges
            </Link>
          </div>
          {college ? (
            <div className="profile-grid">
              <div className="profile-field">
                <div className="k">Region</div>
                <div className="v">{college.region || '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">Placement officer</div>
                <div className="v">{college.pofficer || '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">Officer email</div>
                <div className="v">{college.pemail || '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">Student strength</div>
                <div className="v">{college.student_strength ?? '—'}</div>
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
          <h3>Hosted drives</h3>
          <Link className="btn btn-ghost" to="/host-colleges">
            Manage
          </Link>
        </div>
        {hostings.length === 0 && <div className="empty-state">Not hosting any drives yet.</div>}
        {hostings.map((h) => (
          <div className="list-row" key={h.hid}>
            <div className="list-row-main">
              <div className="list-row-title">{h.hiringRequirement?.jobRole || 'Role'}</div>
              <div className="list-row-sub">
                {h.hiringRequirement?.company?.company_name || '—'} · {h.interview_date || 'Date TBD'}
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="panel">
        <div className="panel-title">
          <h3>Students</h3>
          <Link className="btn btn-ghost" to="/students">
            Manage
          </Link>
        </div>
        {students.length === 0 && <div className="empty-state">No students registered yet.</div>}
        {students.slice(0, 8).map((s) => (
          <div className="list-row" key={s.student_id}>
            <div className="list-row-main">
              <div className="list-row-title">{s.name}</div>
              <div className="list-row-sub">
                {s.branch || '—'} · CGPA {s.cgpa ?? '—'}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
