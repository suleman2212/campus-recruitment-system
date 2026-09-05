import { apiGet, apiPost, apiPut, apiDelete } from './client';

// Maps to CollegePController (@RequestMapping("/participation"))
// insert requires requirementId (rid) and companyId (cid): /participation/insert/{rid}/{cid}
export const fetchParticipations = () => apiGet('/participation/fetch');
export const fetchParticipation = (id) => apiGet(`/participation/fetch/${id}`);
export const createParticipation = (participation, requirementId, companyId) =>
  apiPost(`/participation/insert/${requirementId}/${companyId}`, participation);
export const updateParticipation = (id, participation) =>
  apiPut(`/participation/update/${id}`, participation);
export const deleteParticipation = (id) => apiDelete(`/participation/delete/${id}`);
