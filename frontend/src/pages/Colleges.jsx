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
  { key: 'cname', label: 'Name', render: (r) => r.cname || r.Cname || '—' },
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
      createFn={(values) =>
        createCollege({
          ...values,
          cname: values.cname,
          Cname: values.cname,
          addresss: values.addresss,
          address: values.addresss,
        })
      }
      updateFn={(id, values) =>
        updateCollege(id, {
          ...values,
          cname: values.cname,
          Cname: values.cname,
          addresss: values.addresss,
          address: values.addresss,
        })
      }
      deleteFn={deleteCollege}
      columns={columns}
      fields={fields}
    />
  );
}
