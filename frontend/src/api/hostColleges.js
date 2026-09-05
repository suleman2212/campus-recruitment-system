import { apiGet, apiPost, apiPut, apiDelete } from './client';

// Maps to HostCollege_Controller (@RequestMapping("/hostclg"))
// insert requires requirementId (rid) and collegeId (cid): /hostclg/insert/{rid}/{cid}
export const fetchHostColleges = () => apiGet('/hostclg/fetch');
export const fetchHostCollege = (id) => apiGet(`/hostclg/fetch/${id}`);
export const createHostCollege = (hostClg, requirementId, collegeId) =>
  apiPost(`/hostclg/insert/${requirementId}/${collegeId}`, hostClg);
export const updateHostCollege = (id, hostClg) => apiPut(`/hostclg/update/${id}`, hostClg);
export const deleteHostCollege = (id) => apiDelete(`/hostclg/delete/${id}`);
