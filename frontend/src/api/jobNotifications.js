import { apiGet, apiPost, apiPut, apiDelete } from './client';

// Maps to Job_nController (@RequestMapping("/JobNotification"))
// insert requires requirementId (rid): /JobNotification/insert/{rid}
export const fetchJobNotifications = () => apiGet('/JobNotification/fetch');
export const fetchJobNotification = (id) => apiGet(`/JobNotification/fetch/${id}`);
export const createJobNotification = (notification, requirementId) =>
  apiPost(`/JobNotification/insert/${requirementId}`, notification);
export const updateJobNotification = (id, notification) =>
  apiPut(`/JobNotification/update/${id}`, notification);
export const deleteJobNotification = (id) => apiDelete(`/JobNotification/delete/${id}`);
