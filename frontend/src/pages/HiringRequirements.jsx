import { useEffect, useMemo, useState } from 'react';
import toast from 'react-hot-toast';
import CrudPage from '../components/CrudPage';
import { useAuth } from '../context/AuthContext';
import {
  fetchHiringRequirements,
  createHiringRequirement,
  updateHiringRequirement,
  deleteHiringRequirement,
} from '../api/hiringRequirements';
import { fetchCompanies } from '../api/companies';
import { fetchApplications, createApplication } from '../api/applications';

const fields = [
  { name: 'jobRole', label: 'Job role', required: true },
  { name: 'job_type', label: 'Job type', type: 'select', options: ['Full-time', 'Internship', 'Internship + PPO'] },
  { name: 'required_candidates', label: 'Openings', type: 'number' },
  { name: 'required_skills', label: 'Required skills', type: 'textarea' },
  { name: 'min_cgpa', label: 'Minimum CGPA', type: 'number' },
  { name: 'eligible_branches', label: 'Eligible branches' },
  { name: 'target_region', label: 'Target region' },
  { name: 'application_deadline', label: 'Application deadline', type: 'date' },
  { name: 'created_date', label: 'Created date (epoch, optional)', type: 'number' },
];

const fkFields = [
  {
    name: 'companyId',
    label: 'Company',
    optionsLoader: fetchCompanies,
    mapOption: (c) => ({ value: c.company_id, label: `${c.company_name} (#${c.company_id})` }),
  },
];

const baseColumns = [
  { key: 'requirement_id', label: 'ID', render: (r) => <span className="id-chip">#{r.requirement_id}</span> },
  { key: 'jobRole', label: 'Role' },
  { key: 'job_type', label: 'Type' },
  { key: 'required_candidates', label: 'Openings' },
  { key: 'company', label: 'Company', render: (r) => r.company?.company_name || '—' },
  { key: 'application_deadline', label: 'Deadline' },
];

// Rendered as an extra column, student accounts only: lets a student apply
// straight from the requirements table instead of hunting for the right
// dropdown values on the Applications page.
function ApplyCell({ requirement, studentId, applied, onApplied }) {
  const [submitting, setSubmitting] = useState(false);

  if (applied) {
    return <span className="badge badge-success">Applied</span>;
  }

  const handleApply = async () => {
    setSubmitting(true);
    try {
      await createApplication(
        { application_status: 'Applied', applied_date: new Date().toISOString().slice(0, 10) },
        studentId,
        requirement.requirement_id
      );
      toast.success(`Applied to ${requirement.jobRole || 'this role'}.`);
      onApplied(requirement.requirement_id);
    } catch (err) {
      toast.error(err.message || 'Could not submit application.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <button className="btn btn-brass" style={{ padding: '5px 12px' }} onClick={handleApply} disabled={submitting}>
      {submitting ? 'Applying…' : 'Apply'}
    </button>
  );
}

export default function HiringRequirements() {
  const { role, refId } = useAuth();
  const isStudent = role === 'STUDENT';
  const [appliedIds, setAppliedIds] = useState(new Set());
  const [appliedLoaded, setAppliedLoaded] = useState(false);

  useEffect(() => {
    if (!isStudent || !refId) return;
    let cancelled = false;
    fetchApplications()
      .then((apps) => {
        if (cancelled) return;
        const mine = (Array.isArray(apps) ? apps : []).filter((a) => a.student?.student_id === refId);
        setAppliedIds(new Set(mine.map((a) => a.hiringRequirement?.requirement_id)));
      })
      .catch(() => {})
      .finally(() => {
        if (!cancelled) setAppliedLoaded(true);
      });
    return () => {
      cancelled = true;
    };
  }, [isStudent, refId]);

  const columns = useMemo(() => {
    if (!isStudent) return baseColumns;
    return [
      ...baseColumns,
      {
        key: 'apply',
        label: 'Apply',
        render: (r) =>
          !appliedLoaded ? (
            <span className="empty-state" style={{ padding: 0 }}>
              …
            </span>
          ) : (
            <ApplyCell
              requirement={r}
              studentId={refId}
              applied={appliedIds.has(r.requirement_id)}
              onApplied={(id) => setAppliedIds((prev) => new Set(prev).add(id))}
            />
          ),
      },
    ];
  }, [isStudent, refId, appliedIds, appliedLoaded]);

  return (
    <CrudPage
      eyebrow="Hiring pipeline · 04"
      title="Hiring requirements"
      description="Job openings posted by a company for this hiring cycle."
      idKey="requirement_id"
      listFn={fetchHiringRequirements}
      createFn={(values, fk) => createHiringRequirement(values, fk.companyId)}
      updateFn={(id, values) => updateHiringRequirement(id, values)}
      deleteFn={deleteHiringRequirement}
      columns={columns}
      fields={fields}
      fkFields={fkFields}
      readOnly={isStudent}
      readOnlyNote="Hiring requirements are posted by companies. Hit Apply on a row below to submit your application — it'll also show up on the Applications page."
    />
  );
}
