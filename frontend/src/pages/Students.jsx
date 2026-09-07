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
    mapOption: (c) => ({ value: c.cid, label: `${c.cname || c.Cname || 'College'} (#${c.cid})` }),
  },
];

const columns = [
  { key: 'student_id', label: 'ID', render: (r) => <span className="id-chip">#{r.student_id ?? r.Student_id}</span> },
  { key: 'name', label: 'Name', render: (r) => r.name || r.Name || '—' },
  { key: 'email', label: 'Email', render: (r) => r.email || r.Email || '—' },
  { key: 'branch', label: 'Branch' },
  { key: 'cgpa', label: 'CGPA' },
  { key: 'college', label: 'College', render: (r) => r.college?.cname || r.college?.Cname || '—' },
];

export default function Students() {
  return (
    <CrudPage
      eyebrow="Directory · 02"
      title="Students"
      description="Candidates registered under a host college."
      idKey="student_id"
      listFn={fetchStudents}
      createFn={(values, fk) =>
        createStudent(
          {
            ...values,
            Name: values.name,
            name: values.name,
            Email: values.email,
            email: values.email,
            Phone: values.phone,
            phone: values.phone,
            Resume: values.resume,
            resume: values.resume,
            linkdin: values.linkdin,
            linkedin: values.linkdin,
          },
          fk.collegeId
        )
      }
      updateFn={(id, values) =>
        updateStudent(id, {
          ...values,
          Name: values.name,
          name: values.name,
          Email: values.email,
          email: values.email,
          Phone: values.phone,
          phone: values.phone,
          Resume: values.resume,
          resume: values.resume,
          linkdin: values.linkdin,
          linkedin: values.linkdin,
        })
      }
      deleteFn={deleteStudent}
      columns={columns}
      fields={fields}
      fkFields={fkFields}
    />
  );
}
