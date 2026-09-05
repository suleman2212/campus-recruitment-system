import { apiGet, apiPost, apiPut, apiDelete } from './client';

// Maps to CompanyController (@RequestMapping("/company"))
export const fetchCompanies = () => apiGet('/company/fetch');
export const fetchCompany = (id) => apiGet(`/company/fetch/${id}`);
export const createCompany = (company) => apiPost('/company/insert', company);
export const updateCompany = (id, company) => apiPut(`/company/update/${id}`, company);
export const deleteCompany = (id) => apiDelete(`/company/delete/${id}`);
