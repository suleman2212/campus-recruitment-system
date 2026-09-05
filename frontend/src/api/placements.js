import { apiGet, apiPost, apiPut, apiDelete } from './client';

// Maps to PlacementController (@RequestMapping("/PlacementResult"))
// insert requires applicationId (aid): /PlacementResult/insert/{aid}
export const fetchPlacements = () => apiGet('/PlacementResult/fetch');
export const fetchPlacement = (id) => apiGet(`/PlacementResult/fetch/${id}`);
export const createPlacement = (placement, applicationId) =>
  apiPost(`/PlacementResult/insert/${applicationId}`, placement);
export const updatePlacement = (id, placement) => apiPut(`/PlacementResult/update/${id}`, placement);
export const deletePlacement = (id) => apiDelete(`/PlacementResult/delete/${id}`);
