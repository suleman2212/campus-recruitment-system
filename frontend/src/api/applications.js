import { apiGet, apiPost, apiPut, apiDelete } from './client';

// Maps to ApplicationController (@RequestMapping("/application"))
// insert requires studentId (sid) and requirementId (rid): /application/insert/{sid}/{rid}
export const fetchApplications = () => apiGet('/application/fetch');
export const fetchApplication = (id) => apiGet(`/application/fetch/${id}`);
export const createApplication = (application, studentId, requirementId) =>
  apiPost(`/application/insert/${studentId}/${requirementId}`, application);
export const updateApplication = (id, application) => apiPut(`/application/update/${id}`, application);
export const deleteApplication = (id) => apiDelete(`/application/delete/${id}`);
