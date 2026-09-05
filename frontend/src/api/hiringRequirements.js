import { apiGet, apiPost, apiPut, apiDelete } from './client';

// Maps to HiringRController (@RequestMapping("/HiringR"))
// insert requires the posting Company's id: /HiringR/insert/{cid}
export const fetchHiringRequirements = () => apiGet('/HiringR/fetch');
export const fetchHiringRequirement = (id) => apiGet(`/HiringR/fetch/${id}`);
export const createHiringRequirement = (requirement, companyId) =>
  apiPost(`/HiringR/insert/${companyId}`, requirement);
export const updateHiringRequirement = (id, requirement) =>
  apiPut(`/HiringR/update/${id}`, requirement);
export const deleteHiringRequirement = (id) => apiDelete(`/HiringR/delete/${id}`);
