import { apiGet, apiPost, apiPut, apiDelete } from './client';

// Maps to StrudentController (@RequestMapping("/student"))
// insert requires the id of the College the student belongs to: /student/insert/{cid}
export const fetchStudents = () => apiGet('/student/fetch');
export const fetchStudent = (id) => apiGet(`/student/fetch/${id}`);
export const createStudent = (student, collegeId) =>
  apiPost(`/student/insert/${collegeId}`, student);
export const updateStudent = (id, student) => apiPut(`/student/update/${id}`, student);
export const deleteStudent = (id) => apiDelete(`/student/delete/${id}`);
