import CrudPage from '../components/CrudPage';
import { useAuth } from '../context/AuthContext';
import {
  fetchParticipations,
  createParticipation,
  updateParticipation,
  deleteParticipation,
} from '../api/collegeParticipation';
import { fetchHiringRequirements } from '../api/hiringRequirements';
import { fetchCompanies } from '../api/companies';

const fields = [
  {
    name: 'selection_status',
    label: 'Selection status',
    type: 'select',
    options: ['Selected', 'Not Selected'],
  },
  { name: 'selected_date', label: 'Selected date', type: 'date' },
];

const fkFields = [
  {
    name: 'requirementId',
    label: 'Hiring requirement',
    optionsLoader: fetchHiringRequirements,
    mapOption: (r) => ({ value: r.requirement_id, label: `${r.jobRole || 'Role'} (#${r.requirement_id})` }),
  },
  {
    name: 'companyId',
    label: 'Company',
    optionsLoader: fetchCompanies,
    mapOption: (c) => ({ value: c.company_id, label: `${c.company_name} (#${c.company_id})` }),
  },
];

const columns = [
  { key: 'pid', label: 'ID', render: (r) => <span className="id-chip">#{r.pid}</span> },
  { key: 'requirement', label: 'Requirement', render: (r) => r.hiringRequirement?.jobRole || '—' },
  { key: 'company', label: 'Company', render: (r) => r.company?.company_name || '—' },
  {
    key: 'selection_status',
    label: 'Status',
    render: (r) => <span className="badge badge-brass">{r.selection_status || '—'}</span>,
  },
  { key: 'selected_date', label: 'Selected' },
];

export default function CollegeParticipation() {
  const { role } = useAuth();
  const readOnly = role === 'COMPANY' || role === 'COLLEGE';

  return (
    <CrudPage
      eyebrow="Hiring pipeline · 07"
      title="College participation"
      description="Which companies a hiring requirement is being matched against."
      idKey="pid"
      listFn={fetchParticipations}
      createFn={(values, fk) => createParticipation(values, fk.requirementId, fk.companyId)}
      updateFn={(id, values) => updateParticipation(id, values)}
      deleteFn={deleteParticipation}
      columns={columns}
      fields={fields}
      fkFields={fkFields}
      readOnly={readOnly}
      readOnlyNote="Eligible colleges are grouped automatically based on company requirements and region. You can see the grouping here."
    />
  );
}
