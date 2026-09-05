import CrudPage from '../components/CrudPage';
import { useAuth } from '../context/AuthContext';
import { fetchInterviews, createInterview, updateInterview, deleteInterview } from '../api/interviews';
import { fetchApplications } from '../api/applications';

const fields = [
  {
    name: 'round_name',
    label: 'Round',
    required: true,
    type: 'select',
    options: ['Aptitude', 'Technical', 'HR', 'Other'],
  },
  { name: 'date', label: 'Date', type: 'date' },
  { name: 'time', label: 'Time', type: 'time' },
  { name: 'venue', label: 'Venue' },
  { name: 'mode', label: 'Mode', type: 'select', options: ['Online', 'Offline'] },
  {
    name: 'status',
    label: 'Status',
    type: 'select',
    options: ['Scheduled', 'Completed', 'Cancelled'],
  },
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
  { key: 'interview_id', label: 'ID', render: (r) => <span className="id-chip">#{r.interview_id}</span> },
  { key: 'round_name', label: 'Round' },
  { key: 'application', label: 'Applicant', render: (r) => r.application?.student?.name || '—' },
  { key: 'date', label: 'Date' },
  { key: 'time', label: 'Time' },
  { key: 'mode', label: 'Mode' },
  { key: 'status', label: 'Status', render: (r) => <span className="badge badge-brass">{r.status || '—'}</span> },
];

export default function Interviews() {
  const { role } = useAuth();
  const readOnly = role === 'STUDENT' || role === 'COLLEGE';

  return (
    <CrudPage
      eyebrow="Hiring pipeline · 09"
      title="Interview schedule"
      description="Rounds scheduled for a student's application."
      idKey="interview_id"
      listFn={fetchInterviews}
      createFn={(values, fk) => createInterview(values, fk.applicationId)}
      updateFn={(id, values) => updateInterview({ ...values, interview_id: id })}
      deleteFn={deleteInterview}
      columns={columns}
      fields={fields}
      fkFields={fkFields}
      readOnly={readOnly}
      readOnlyNote="Interview rounds are scheduled by the company running the drive at the host college. You can see your schedule here."
    />
  );
}
