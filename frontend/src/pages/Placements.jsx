import CrudPage from '../components/CrudPage';
import { useAuth } from '../context/AuthContext';
import { fetchPlacements, createPlacement, updatePlacement, deletePlacement } from '../api/placements';
import { fetchApplications } from '../api/applications';

const fields = [
  {
    name: 'selection_status',
    label: 'Selection status',
    type: 'select',
    required: true,
    options: ['Selected', 'Not Selected', 'Waitlisted', 'Rejected'],
  },
  { name: 'package_offered', label: 'Package offered', placeholder: 'e.g. 12 LPA' },
  { name: 'remarks', label: 'Remarks', type: 'textarea' },
  { name: 'result_date', label: 'Result date', type: 'date' },
];

const fkFields = [
  {
    name: 'applicationId',
    label: 'Application',
    optionsLoader: fetchApplications,
    mapOption: (a) => ({
      value: a.application_id,
      label: `${a.student?.name || 'Student'} → ${a.hiringRequirement?.jobRole || 'Role'} (#${a.application_id})`,
    }),
  },
];

const columns = [
  { key: 'result_id', label: 'ID', render: (r) => <span className="id-chip">#{r.result_id}</span> },
  { key: 'application', label: 'Applicant', render: (r) => r.application?.student?.name || '—' },
  {
    key: 'requirement',
    label: 'Role',
    render: (r) => r.application?.hiringRequirement?.jobRole || '—',
  },
  {
    key: 'selection_status',
    label: 'Result',
    render: (r) => (
      <span className={`badge ${r.selection_status === 'Selected' ? 'badge-success' : 'badge-danger'}`}>
        {r.selection_status || '—'}
      </span>
    ),
  },
  { key: 'package_offered', label: 'Package' },
  { key: 'result_date', label: 'Result date' },
];

export default function Placements() {
  const { role } = useAuth();
  const readOnly = role === 'STUDENT' || role === 'COLLEGE';

  return (
    <CrudPage
      eyebrow="Hiring pipeline · 10"
      title="Placement results"
      description="Final outcome recorded against a student's application."
      idKey="result_id"
      listFn={fetchPlacements}
      createFn={(values, fk) => createPlacement(values, fk.applicationId)}
      updateFn={(id, values) => updatePlacement(id, values)}
      deleteFn={deletePlacement}
      columns={columns}
      fields={fields}
      fkFields={fkFields}
      readOnly={readOnly}
      readOnlyNote="Results are published once selections are finalized. You can see the outcome here as soon as it's posted."
    />
  );
}
