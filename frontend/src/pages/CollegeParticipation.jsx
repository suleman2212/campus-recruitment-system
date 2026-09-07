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
import { fetchColleges } from '../api/colleges';

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
  {
    name: 'collegeId',
    label: 'Participating College',
    required: false,
    optionsLoader: fetchColleges,
    mapOption: (c) => ({ value: c.cid, label: `${c.cname} (#${c.cid})` }),
  },
];

const columns = [
  { key: 'pid', label: 'ID', render: (r) => <span className="id-chip">#{r.pid}</span> },
  { key: 'requirement', label: 'Requirement', render: (r) => r.hiringRequirement?.jobRole || '—' },
  { key: 'company', label: 'Company', render: (r) => r.company?.company_name || '—' },
  { key: 'college', label: 'College', render: (r) => r.college?.cname || '—' },
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
      description="Which colleges and companies a hiring requirement is being matched against."
      idKey="pid"
      listFn={fetchParticipations}
      createFn={(values, fk) => {
        const payload = { ...values };
        if (fk.collegeId) {
          payload.college = { cid: Number(fk.collegeId) };
        }
        return createParticipation(payload, fk.requirementId, fk.companyId);
      }}
      updateFn={(id, values) => updateParticipation(id, values)}
      deleteFn={deleteParticipation}
      columns={columns}
      fields={fields}
      fkFields={fkFields}
      readOnly={readOnly}
      readOnlyNote="Eligible colleges are grouped based on company requirements and region. You can track participation records here."
    />
  );
}
