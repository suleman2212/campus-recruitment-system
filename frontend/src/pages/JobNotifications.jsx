import CrudPage from '../components/CrudPage';
import { useAuth } from '../context/AuthContext';
import {
  fetchJobNotifications,
  createJobNotification,
  updateJobNotification,
  deleteJobNotification,
} from '../api/jobNotifications';
import { fetchHiringRequirements } from '../api/hiringRequirements';

const fields = [
  { name: 'title', label: 'Title', required: true },
  { name: 'discription', label: 'Description', type: 'textarea' },
  { name: 'publish_date', label: 'Publish date', type: 'date' },
];

const fkFields = [
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
  { key: 'notification_id', label: 'ID', render: (r) => <span className="id-chip">#{r.notification_id}</span> },
  { key: 'title', label: 'Title' },
  { key: 'publish_date', label: 'Published' },
  { key: 'requirement', label: 'Requirement', render: (r) => r.hiringRequirement?.jobRole || '—' },
];

export default function JobNotifications() {
  const { role } = useAuth();
  const readOnly = role === 'STUDENT' || role === 'COLLEGE';

  return (
    <CrudPage
      eyebrow="Hiring pipeline · 05"
      title="Job notifications"
      description="Announcements published for an open hiring requirement."
      idKey="notification_id"
      listFn={fetchJobNotifications}
      createFn={(values, fk) => createJobNotification(values, fk.requirementId)}
      updateFn={(id, values) => updateJobNotification(id, values)}
      deleteFn={deleteJobNotification}
      columns={columns}
      fields={fields}
      fkFields={fkFields}
      readOnly={readOnly}
      readOnlyNote="Job notifications are released once eligible colleges are grouped for a requirement. You can see everything published here."
    />
  );
}
