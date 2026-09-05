import CrudPage from '../components/CrudPage';
import { fetchStudents, createStudent, updateStudent, deleteStudent } from '../api/students';
import { fetchColleges } from '../api/colleges';

const fields = [
  { name: 'name', label: 'Full name', required: true },
  { name: 'email', label: 'Email', type: 'email', required: true },
  { name: 'phone', label: 'Phone', type: 'number' },
  { name: 'branch', label: 'Branch' },
  { name: 'cgpa', label: 'CGPA', type: 'number' },
  { name: 'graduation_year', label: 'Graduation year', type: 'number' },
  { name: 'skills', label: 'Skills', type: 'textarea', placeholder: 'Comma separated' },
  { name: 'linkdin', label: 'LinkedIn URL' },
  { name: 'github', label: 'GitHub URL' },
  { name: 'resume', label: 'Resume URL' },
];

const fkFields = [
  {
    name: 'collegeId',
    label: 'College',
    optionsLoader: fetchColleges,
    mapOption: (c) => ({ value: c.cid, label: `${c.cname} (#${c.cid})` }),
  },
];

const columns = [
  { key: 'student_id', label: 'ID', render: (r) => <span className="id-chip">#{r.student_id}</span> },
  { key: 'name', label: 'Name' },
  { key: 'email', label: 'Email' },
  { key: 'branch', label: 'Branch' },
  { key: 'cgpa', label: 'CGPA' },
  { key: 'college', label: 'College', render: (r) => r.college?.cname || '—' },
];

export default function Students() {
  return (
    <CrudPage
      eyebrow="Directory · 02"
      title="Students"
      description="Candidates registered under a host college."
      idKey="student_id"
      listFn={fetchStudents}
      createFn={(values, fk) => createStudent(values, fk.collegeId)}
      updateFn={(id, values) => updateStudent(id, values)}
      deleteFn={deleteStudent}
      columns={columns}
      fields={fields}
      fkFields={fkFields}
    />
  );
}
