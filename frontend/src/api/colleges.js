import { apiGet, apiPost, apiPut, apiDelete } from './client';

// Maps to CollegeController (@RequestMapping("/college"))
export const fetchColleges = () => apiGet('/college/fetch');
export const fetchCollege = (id) => apiGet(`/college/fetch/${id}`);
export const createCollege = (college) => apiPost('/college/insert', college);
export const updateCollege = (id, college) => apiPut(`/college/update/${id}`, college);
export const deleteCollege = (id) => apiDelete(`/college/delete/${id}`);
