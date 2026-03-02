import axios from 'axios'

const api = axios.create({
  baseURL: '',
  headers: {
    'Content-Type': 'application/json'
  }
})

export const identityAPI = {
  getUsers: () => api.get('/api/identity'),
  createUser: (data) => api.post('/api/identity', data)
}

export const dashboardSummaryAPI = {
  getSummary: () => api.get('/api/claims/dashboard'),
  getKpis: () => api.get('/api/analytics'),
  getNotifications: () => api.get('/api/notifications')
}

// Data Ingestion Service
export const dataIngestionAPI = {
  getAllFeeds: () => api.get('/api/data-ingestion/feeds'),
  createFeed: (data) => api.post('/api/data-ingestion/feeds', data),
  ingestClaim: (data) => api.post('/api/data-ingestion/claims/raw', data),
  getPendingClaims: () => api.get('/api/data-ingestion/claims/raw/pending')
}

// Fraud Risk Service
export const fraudRiskAPI = {
  createIndicator: (data) => api.post('/api/fraud-risk/indicators', data),
  getIndicatorsByClaimId: (claimId) => api.get(`/api/fraud-risk/indicators/claim/${claimId}`),
  getUnresolvedIndicators: () => api.get('/api/fraud-risk/indicators/unresolved'),
  calculateRiskScore: (claimId) => api.post(`/api/fraud-risk/scores/calculate/${claimId}`),
  getRiskScore: (claimId) => api.get(`/api/fraud-risk/scores/claim/${claimId}`),
  getHighRiskClaims: () => api.get('/api/fraud-risk/scores/high-risk')
}

// Denial Analysis Service
export const denialAnalysisAPI = {
  recordDenial: (data) => api.post('/api/denial-analysis/denials', data),
  getDenialsByClaimId: (claimId) => api.get(`/api/denial-analysis/denials/claim/${claimId}`),
  getDenialAnalysis: () => api.get('/api/denial-analysis/denials/analysis'),
  flagLeakage: (data) => api.post('/api/denial-analysis/leakages', data),
  getUninvestigatedLeakages: () => api.get('/api/denial-analysis/leakages/uninvestigated'),
  getTotalLeakageAmount: () => api.get('/api/denial-analysis/leakages/total-amount')
}

// Performance Analytics Service
export const performanceAPI = {
  recordPerformance: (data) => api.post('/api/performance/adjuster', data),
  getPerformanceByAdjuster: (adjusterId) => api.get(`/api/performance/adjuster/${adjusterId}`),
  getAllPerformance: () => api.get('/api/performance/adjuster/all'),
  recordSLAViolation: (data) => api.post('/api/performance/sla-violations', data),
  getUnresolvedViolations: () => api.get('/api/performance/sla-violations/unresolved')
}

// Cost Reserve Service
export const costReserveAPI = {
  recordCost: (data) => api.post('/api/cost-reserve/costs', data),
  getCostsByClaimId: (claimId) => api.get(`/api/cost-reserve/costs/claim/${claimId}`),
  updateReserve: (data) => api.post('/api/cost-reserve/reserves', data),
  getReserveByClaimId: (claimId) => api.get(`/api/cost-reserve/reserves/claim/${claimId}`),
  updateAging: (data) => api.post('/api/cost-reserve/aging', data),
  getStaleClaims: () => api.get('/api/cost-reserve/aging/stale')
}

// Dashboard Service
export const dashboardAPI = {
  generateReport: (data) => api.post('/api/dashboard/reports', data),
  getAllReports: () => api.get('/api/dashboard/reports'),
  getReportById: (id) => api.get(`/api/dashboard/reports/${id}`),
  getReportsByType: (type) => api.get(`/api/dashboard/reports/type/${type}`)
}

// Notification Service
export const notificationAPI = {
  getAllNotifications: () => api.get('/api/notifications'),
  markAsRead: (id) => api.put(`/api/notifications/${id}/read`, { status: 'READ' })
}

export default api
