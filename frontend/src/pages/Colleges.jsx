import CrudPage from '../components/CrudPage';
import { fetchColleges, createCollege, updateCollege, deleteCollege } from '../api/colleges';

const fields = [
  { name: 'cname', label: 'College name', required: true },
  { name: 'region', label: 'Region' },
  { name: 'addresss', label: 'Address' },
  { name: 'pofficer', label: 'Placement officer' },
  { name: 'pemail', label: 'Officer email', type: 'email' },
  { name: 'pnumber', label: 'Officer phone', type: 'number' },
  { name: 'infra_score', label: 'Infrastructure score' },
  { name: 'student_strength', label: 'Student strength', type: 'number' },
];

const columns = [
  { key: 'cid', label: 'ID', render: (r) => <span className="id-chip">#{r.cid}</span> },
  { key: 'cname', label: 'Name' },
  { key: 'region', label: 'Region' },
  { key: 'pofficer', label: 'Officer' },
  { key: 'pemail', label: 'Email' },
  { key: 'student_strength', label: 'Strength' },
];

export default function Colleges() {
  return (
    <CrudPage
      eyebrow="Directory · 01"
      title="Colleges"
      description="Institutions participating in campus recruitment."
      idKey="cid"
      listFn={fetchColleges}
      createFn={(values) => createCollege(values)}
      updateFn={(id, values) => updateCollege(id, values)}
      deleteFn={deleteCollege}
      columns={columns}
      fields={fields}
    />
  );
}
