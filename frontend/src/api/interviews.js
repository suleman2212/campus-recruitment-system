import { apiGet, apiPost, apiPut, apiDelete } from './client';

// Maps to InterviewController (@RequestMapping("/interviewSchedule"))
// insert requires applicationId (aid): /interviewSchedule/insert/{aid}
// NOTE: update has no {id} in the path - the backend looks up the row using
// interview_id inside the request body, so it must be included when editing.
export const fetchInterviews = () => apiGet('/interviewSchedule/fetch');
export const fetchInterview = (id) => apiGet(`/interviewSchedule/fetch/${id}`);
export const createInterview = (interview, applicationId) =>
  apiPost(`/interviewSchedule/insert/${applicationId}`, interview);
export const updateInterview = (interview) => apiPut('/interviewSchedule/update', interview);
export const deleteInterview = (id) => apiDelete(`/interviewSchedule/delete/${id}`);
