import CrudPage from '../components/CrudPage';
import { fetchCompanies, createCompany, updateCompany, deleteCompany } from '../api/companies';

const fields = [
  { name: 'company_name', label: 'Company name', required: true },
  { name: 'location', label: 'Location' },
  { name: 'email', label: 'Email', type: 'email' },
  { name: 'website', label: 'Website' },
  { name: 'phone', label: 'Phone', type: 'number' },
];

const columns = [
  { key: 'company_id', label: 'ID', render: (r) => <span className="id-chip">#{r.company_id}</span> },
  { key: 'company_name', label: 'Company', render: (r) => r.company_name || r.companyName || '—' },
  { key: 'location', label: 'Location', render: (r) => r.location || r.Location || '—' },
  { key: 'email', label: 'Email' },
  { key: 'website', label: 'Website' },
  { key: 'phone', label: 'Phone' },
];

export default function Companies() {
  return (
    <CrudPage
      eyebrow="Directory · 03"
      title="Companies"
      description="Recruiters hiring through the placement cell."
      idKey="company_id"
      listFn={fetchCompanies}
      createFn={(values) =>
        createCompany({
          ...values,
          Location: values.location,
          location: values.location,
          company_name: values.company_name,
          companyName: values.company_name,
        })
      }
      updateFn={(id, values) =>
        updateCompany(id, {
          ...values,
          Location: values.location,
          location: values.location,
          company_name: values.company_name,
          companyName: values.company_name,
        })
      }
      deleteFn={deleteCompany}
      columns={columns}
      fields={fields}
    />
  );
}
