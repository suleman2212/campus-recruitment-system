import CrudPage from '../components/CrudPage';
import { useAuth } from '../context/AuthContext';
import { fetchHostColleges, createHostCollege, updateHostCollege, deleteHostCollege } from '../api/hostColleges';
import { fetchHiringRequirements } from '../api/hiringRequirements';
import { fetchColleges } from '../api/colleges';

const fields = [
  { name: 'interview_date', label: 'Interview date', type: 'date' },
  { name: 'venue', label: 'Venue' },
  { name: 'selection_reason', label: 'Selection reason', type: 'textarea' },
];

const fkFields = [
  {
    name: 'requirementId',
    label: 'Hiring requirement',
    optionsLoader: fetchHiringRequirements,
    mapOption: (r) => ({ value: r.requirement_id, label: `${r.jobRole || 'Role'} (#${r.requirement_id})` }),
  },
  {
    name: 'collegeId',
    label: 'Host college',
    optionsLoader: fetchColleges,
    mapOption: (c) => ({ value: c.cid, label: `${c.cname} (#${c.cid})` }),
  },
];

const columns = [
  { key: 'hid', label: 'ID', render: (r) => <span className="id-chip">#{r.hid}</span> },
  { key: 'requirement', label: 'Requirement', render: (r) => r.hiringRequirement?.jobRole || '—' },
  { key: 'college', label: 'College', render: (r) => r.college?.cname || '—' },
  { key: 'interview_date', label: 'Interview date' },
  { key: 'venue', label: 'Venue' },
];

export default function HostColleges() {
  const { role } = useAuth();
  const readOnly = role === 'COLLEGE';

  return (
    <CrudPage
      eyebrow="Hiring pipeline · 08"
      title="Host colleges"
      description="Which college is hosting on-campus interviews for a requirement."
      idKey="hid"
      listFn={fetchHostColleges}
      createFn={(values, fk) => createHostCollege(values, fk.requirementId, fk.collegeId)}
      updateFn={(id, values) => updateHostCollege(id, values)}
      deleteFn={deleteHostCollege}
      columns={columns}
      fields={fields}
      fkFields={fkFields}
      readOnly={readOnly}
      readOnlyNote="The best-infrastructure host college is selected automatically for each requirement. You can see the result here."
    />
  );
}
