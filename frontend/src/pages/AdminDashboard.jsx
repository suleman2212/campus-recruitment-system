import { useEffect, useState } from 'react';
import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip } from 'recharts';
import { fetchColleges } from '../api/colleges';
import { fetchStudents } from '../api/students';
import { fetchCompanies } from '../api/companies';
import { fetchHiringRequirements } from '../api/hiringRequirements';
import { fetchJobNotifications } from '../api/jobNotifications';
import { fetchParticipations } from '../api/collegeParticipation';
import { fetchHostColleges } from '../api/hostColleges';
import { fetchApplications } from '../api/applications';
import { fetchInterviews } from '../api/interviews';
import { fetchPlacements } from '../api/placements';
import WorkflowTracker from '../components/WorkflowTracker';

const DIRECTORY_SOURCES = [
  { label: 'Colleges', loader: fetchColleges },
  { label: 'Students', loader: fetchStudents },
  { label: 'Companies', loader: fetchCompanies },
];

const PIPELINE_SOURCES = [
  { key: 'requirements', label: 'Requirement posted', loader: fetchHiringRequirements },
  { key: 'notifications', label: 'Notification released', loader: fetchJobNotifications },
  { key: 'participation', label: 'Colleges grouped', loader: fetchParticipations },
  { key: 'hostColleges', label: 'Host college set', loader: fetchHostColleges },
  { key: 'applications', label: 'Students applied', loader: fetchApplications },
  { key: 'interviews', label: 'Interviews run', loader: fetchInterviews },
  { key: 'placements', label: 'Results published', loader: fetchPlacements },
];

const STATUS_COLORS = {
  Applied: '#8f6f3f',
  Shortlisted: '#b08d57',
  Interviewing: '#5b6472',
  Selected: '#3f7a5e',
  Rejected: '#a2402f',
};

export default function AdminDashboard() {
  const [directoryStats, setDirectoryStats] = useState(DIRECTORY_SOURCES.map((s) => ({ ...s, count: null })));
  const [pipelineStats, setPipelineStats] = useState(PIPELINE_SOURCES.map((s) => ({ ...s, count: null })));
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    let cancelled = false;

    DIRECTORY_SOURCES.forEach(async (source, i) => {
      try {
        const data = await source.loader();
        if (!cancelled) {
          setDirectoryStats((prev) => {
            const next = [...prev];
            next[i] = { ...next[i], count: Array.isArray(data) ? data.length : 0 };
            return next;
          });
        }
      } catch {
        if (!cancelled) {
          setDirectoryStats((prev) => {
            const next = [...prev];
            next[i] = { ...next[i], count: '—' };
            return next;
          });
        }
      }
    });

    PIPELINE_SOURCES.forEach(async (source, i) => {
      try {
        const data = await source.loader();
        if (!cancelled) {
          setPipelineStats((prev) => {
            const next = [...prev];
            next[i] = { ...next[i], count: Array.isArray(data) ? data.length : 0 };
            return next;
          });
          if (source.key === 'applications' && Array.isArray(data)) setApplications(data);
        }
      } catch {
        if (!cancelled) {
          setPipelineStats((prev) => {
            const next = [...prev];
            next[i] = { ...next[i], count: 0 };
            return next;
          });
        }
      }
    });

    return () => {
      cancelled = true;
    };
  }, []);

  const statusBreakdown = Object.entries(
    applications.reduce((acc, a) => {
      const status = a.application_status || 'Applied';
      acc[status] = (acc[status] || 0) + 1;
      return acc;
    }, {})
  ).map(([name, value]) => ({ name, value }));

  return (
    <div>
      <div className="page-header">
        <div>
          <span className="page-eyebrow">Overview</span>
          <h1>Placement dashboard</h1>
          <p>Live counts pulled straight from the Spring Boot API on load.</p>
        </div>
      </div>

      <div className="stat-row">
        {directoryStats.map((s) => (
          <div className="stat-card" key={s.label}>
            <div className="num">{s.count === null ? '…' : s.count}</div>
            <div className="lbl">{s.label}</div>
          </div>
        ))}
      </div>

      <WorkflowTracker
        eyebrow="Drive pipeline · live"
        title="Where every requirement stands, end to end"
        stages={pipelineStats}
      />

      <div className="dash-grid">
        <div className="panel">
          <div className="panel-title">
            <h3>Getting started</h3>
          </div>
          <p style={{ color: 'var(--slate)', fontSize: '0.9rem', lineHeight: 1.6, margin: 0 }}>
            Start by setting up your <strong>Directory</strong> — the colleges, students and companies
            everything else is built on. From there, work down the <strong>Hiring pipeline</strong>: post a
            requirement, release the notification, let eligible colleges group automatically, confirm a host
            college, then track applications through interviews to a final placement result.
          </p>
        </div>

        <div className="panel">
          <div className="panel-title">
            <h3>Applications by status</h3>
          </div>
          {statusBreakdown.length === 0 ? (
            <div className="empty-state">No applications yet.</div>
          ) : (
            <>
              <ResponsiveContainer width="100%" height={180}>
                <PieChart>
                  <Pie
                    data={statusBreakdown}
                    dataKey="value"
                    nameKey="name"
                    innerRadius={45}
                    outerRadius={72}
                    paddingAngle={2}
                  >
                    {statusBreakdown.map((entry) => (
                      <Cell key={entry.name} fill={STATUS_COLORS[entry.name] || '#5b6472'} />
                    ))}
                  </Pie>
                  <Tooltip
                    contentStyle={{
                      background: 'var(--ink)',
                      border: 'none',
                      borderRadius: 6,
                      color: '#fff',
                      fontSize: '0.8rem',
                    }}
                  />
                </PieChart>
              </ResponsiveContainer>
              <div className="chart-legend-row">
                {statusBreakdown.map((entry) => (
                  <span className="chart-legend-item" key={entry.name}>
                    <span
                      className="chart-legend-dot"
                      style={{ background: STATUS_COLORS[entry.name] || '#5b6472' }}
                    />
                    {entry.name} ({entry.value})
                  </span>
                ))}
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
