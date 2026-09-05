import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { fetchCompany } from '../api/companies';
import { fetchHiringRequirements } from '../api/hiringRequirements';
import { fetchApplications } from '../api/applications';
import { fetchHostColleges } from '../api/hostColleges';
import { fetchParticipations } from '../api/collegeParticipation';
import { fetchInterviews } from '../api/interviews';
import { fetchPlacements } from '../api/placements';
import { useAuth } from '../context/AuthContext';
import WorkflowTracker from '../components/WorkflowTracker';

export default function CompanyDashboard() {
  const { refId, username } = useAuth();
  const [company, setCompany] = useState(null);
  const [requirements, setRequirements] = useState([]);
  const [applications, setApplications] = useState([]);
  const [hostColleges, setHostColleges] = useState([]);
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
        const [companyData, reqs, apps, hosts, participationRows, interviewRows, placementRows] = await Promise.all([
          refId ? fetchCompany(refId) : Promise.resolve(null),
          fetchHiringRequirements(),
          fetchApplications(),
          fetchHostColleges(),
          fetchParticipations(),
          fetchInterviews(),
          fetchPlacements(),
        ]);
        if (cancelled) return;
        setCompany(companyData);
        const myReqs = (Array.isArray(reqs) ? reqs : []).filter((r) => r.company?.company_id === refId);
        setRequirements(myReqs);
        const myReqIds = new Set(myReqs.map((r) => r.requirement_id));
        const myApps = (Array.isArray(apps) ? apps : []).filter((a) => myReqIds.has(a.hiringRequirement?.requirement_id));
        setApplications(myApps);
        setHostColleges((Array.isArray(hosts) ? hosts : []).filter((h) => myReqIds.has(h.hiringRequirement?.requirement_id)));
        setParticipations((Array.isArray(participationRows) ? participationRows : []).filter((p) => myReqIds.has(p.hiringRequirement?.requirement_id)));
        const myAppIds = new Set(myApps.map((a) => a.application_id));
        setInterviews((Array.isArray(interviewRows) ? interviewRows : []).filter((i) => myAppIds.has(i.application?.application_id)));
        setPlacements((Array.isArray(placementRows) ? placementRows : []).filter((p) => myAppIds.has(p.application?.application_id)));
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
    { key: 'requirements', label: 'Requirement posted', count: loading ? null : requirements.length },
    { key: 'participation', label: 'Colleges grouped', count: loading ? null : participations.length },
    { key: 'hostColleges', label: 'Host college set', count: loading ? null : hostColleges.length },
    { key: 'applications', label: 'Students applied', count: loading ? null : applications.length },
    { key: 'interviews', label: 'Interviews run', count: loading ? null : interviews.length },
    { key: 'placements', label: 'Results published', count: loading ? null : placements.length },
  ];

  return (
    <div>
      <div className="page-header">
        <div>
          <span className="page-eyebrow">Company · Overview</span>
          <h1>Welcome{company?.company_name ? `, ${company.company_name}` : `, ${username}`}</h1>
          <p>Post hiring requirements and track applications and host colleges.</p>
        </div>
        <Link className="btn btn-brass" to="/requirements">
          Post a hiring requirement
        </Link>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      <div className="stat-row">
        <div className="stat-card">
          <div className="num">{loading ? '…' : requirements.length}</div>
          <div className="lbl">Open requirements</div>
        </div>
        <div className="stat-card">
          <div className="num">{loading ? '…' : applications.length}</div>
          <div className="lbl">Applications received</div>
        </div>
        <div className="stat-card">
          <div className="num">{loading ? '…' : hostColleges.length}</div>
          <div className="lbl">Host colleges</div>
        </div>
      </div>

      <WorkflowTracker
        eyebrow="Your drive · live"
        title="Your requirements, stage by stage"
        stages={pipelineStages}
      />

      <div className="dash-grid">
        <div className="panel">
          <div className="panel-title">
            <h3>Company profile</h3>
            <Link className="btn btn-ghost" to="/companies">
              Edit in Companies
            </Link>
          </div>
          {company ? (
            <div className="profile-grid">
              <div className="profile-field">
                <div className="k">Location</div>
                <div className="v">{company.location || '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">Email</div>
                <div className="v">{company.email || '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">Website</div>
                <div className="v">{company.website || '—'}</div>
              </div>
              <div className="profile-field">
                <div className="k">Phone</div>
                <div className="v">{company.phone || '—'}</div>
              </div>
            </div>
          ) : (
            <div className="empty-state">{loading ? 'Loading…' : 'Profile not found.'}</div>
          )}
        </div>

        <div className="panel">
          <div className="panel-title">
            <h3>Host colleges</h3>
            <Link className="btn btn-ghost" to="/host-colleges">
              Manage
            </Link>
          </div>
          {hostColleges.length === 0 && <div className="empty-state">No colleges confirmed yet.</div>}
          {hostColleges.map((h) => (
            <div className="list-row" key={h.hid}>
              <div className="list-row-main">
                <div className="list-row-title">{h.college?.cname || 'College'}</div>
                <div className="list-row-sub">{h.interview_date || 'Date TBD'} · {h.venue || 'Venue TBD'}</div>
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="panel">
        <div className="panel-title">
          <h3>Your hiring requirements</h3>
          <Link className="btn btn-ghost" to="/requirements">
            View all
          </Link>
        </div>
        {requirements.length === 0 && <div className="empty-state">You haven't posted a requirement yet.</div>}
        {requirements.map((r) => (
          <div className="list-row" key={r.requirement_id}>
            <div className="list-row-main">
              <div className="list-row-title">{r.jobRole}</div>
              <div className="list-row-sub">
                {r.job_type || '—'} · {r.required_candidates ?? '—'} openings · deadline {r.application_deadline || '—'}
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="panel">
        <div className="panel-title">
          <h3>Recent applications</h3>
          <Link className="btn btn-ghost" to="/applications">
            View all
          </Link>
        </div>
        {applications.length === 0 && <div className="empty-state">No applications yet.</div>}
        {applications.slice(0, 8).map((a) => (
          <div className="list-row" key={a.application_id}>
            <div className="list-row-main">
              <div className="list-row-title">{a.student?.name || 'Student'}</div>
              <div className="list-row-sub">
                {a.hiringRequirement?.jobRole || '—'} · applied {a.applied_date || '—'}
              </div>
            </div>
            <span className="badge badge-brass">{a.application_status || 'Applied'}</span>
          </div>
        ))}
      </div>
    </div>
  );
}
