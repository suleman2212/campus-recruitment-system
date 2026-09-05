import CrudPage from '../components/CrudPage';
import { useAuth } from '../context/AuthContext';
import { fetchApplications, createApplication, updateApplication, deleteApplication } from '../api/applications';
import { fetchStudents } from '../api/students';
import { fetchHiringRequirements } from '../api/hiringRequirements';

const fields = [
  {
    name: 'application_status',
    label: 'Status',
    type: 'select',
    options: ['Applied', 'Shortlisted', 'Rejected', 'Selected'],
  },
  { name: 'applied_date', label: 'Applied date', type: 'date' },
];

const fkFields = [
  {
    name: 'studentId',
    label: 'Student',
    optionsLoader: fetchStudents,
    mapOption: (s) => ({ value: s.student_id, label: `${s.name} (#${s.student_id})` }),
  },
  {
    name: 'requirementId',
    label: 'Hiring requirement',
    optionsLoader: fetchHiringRequirements,
    mapOption: (r) => ({
      value: r.requirement_id,
      label: `${r.jobRole || 'Role'} @ ${r.company?.company_name || '—'} (#${r.requirement_id})`,
    }),
  },
];

const columns = [
  { key: 'application_id', label: 'ID', render: (r) => <span className="id-chip">#{r.application_id}</span> },
  { key: 'student', label: 'Student', render: (r) => r.student?.name || '—' },
  { key: 'requirement', label: 'Role', render: (r) => r.hiringRequirement?.jobRole || '—' },
  {
    key: 'application_status',
    label: 'Status',
    render: (r) => <span className="badge badge-brass">{r.application_status || '—'}</span>,
  },
  { key: 'applied_date', label: 'Applied' },
];

export default function Applications() {
  const { role } = useAuth();
  const readOnly = role === 'COMPANY';

  return (
    <CrudPage
      eyebrow="Hiring pipeline · 06"
      title="Applications"
      description="A student's application against a specific hiring requirement."
      idKey="application_id"
      listFn={fetchApplications}
      createFn={(values, fk) => createApplication(values, fk.studentId, fk.requirementId)}
      updateFn={(id, values) => updateApplication(id, values)}
      deleteFn={deleteApplication}
      columns={columns}
      fields={fields}
      fkFields={fkFields}
      readOnly={readOnly}
      readOnlyNote="Applications are submitted by students. As a company, you can review who has applied to your requirements here."
    />
  );
}
