import { useEffect, useMemo, useState } from 'react'
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts'
import { dashboardSummaryAPI, dataIngestionAPI, fraudRiskAPI, denialAnalysisAPI } from '../api'

export default function Dashboard() {
  const [loading, setLoading] = useState(true)
  const [summary, setSummary] = useState(null)
  const [pendingClaims, setPendingClaims] = useState([])
  const [highRisk, setHighRisk] = useState([])
  const [totalLeakage, setTotalLeakage] = useState(0)
  const [kpis, setKpis] = useState([])

  useEffect(() => {
    const loadData = async () => {
      try {
        const [summaryRes, pendingRes, highRiskRes, leakageRes, kpiRes] = await Promise.all([
          dashboardSummaryAPI.getSummary(),
          dataIngestionAPI.getPendingClaims(),
          fraudRiskAPI.getHighRiskClaims(),
          denialAnalysisAPI.getTotalLeakageAmount(),
          dashboardSummaryAPI.getKpis()
        ])

        setSummary(summaryRes.data)
        setPendingClaims(pendingRes.data || [])
        setHighRisk(highRiskRes.data || [])
        setTotalLeakage(Number(leakageRes.data || 0))
        setKpis(kpiRes.data || [])
      } catch (error) {
        console.error('Error loading dashboard data:', error)
      } finally {
        setLoading(false)
      }
    }

    loadData()
  }, [])

  const chartData = useMemo(() => {
    if (kpis.length === 0) {
      return []
    }

    const grouped = new Map()
    kpis.forEach((kpi) => {
      const dateKey = (kpi.metricDate || '').slice(0, 7)
      if (!dateKey) return
      if (!grouped.has(dateKey)) {
        grouped.set(dateKey, { month: dateKey, kpiCount: 0, metricValue: 0 })
      }
      const current = grouped.get(dateKey)
      current.kpiCount += 1
      current.metricValue += Number(kpi.metricValue || 0)
    })

    return Array.from(grouped.values()).sort((a, b) => a.month.localeCompare(b.month))
  }, [kpis])

  if (loading) return <div className="loading">Loading...</div>

  return (
    <div>
      <div className="page-header">
        <h2>Dashboard</h2>
        <p>Overview of your claims analytics and key metrics</p>
      </div>

      <div className="dashboard-grid">
        <div className="stat-card blue">
          <div className="stat-label">Total Claims</div>
          <div className="stat-value">{summary?.totalClaims ?? pendingClaims.length}</div>
          <div className="stat-change positive">Live from Claims service</div>
        </div>
        
        <div className="stat-card yellow">
          <div className="stat-label">Pending Claims</div>
          <div className="stat-value">{pendingClaims.length}</div>
          <div className="stat-change positive">Live from Data Ingestion service</div>
        </div>
        
        <div className="stat-card red">
          <div className="stat-label">High Risk Claims</div>
          <div className="stat-value">{highRisk.length}</div>
          <div className="stat-change negative">Live from Fraud Risk service</div>
        </div>
        
        <div className="stat-card green">
          <div className="stat-label">Total Leakage</div>
          <div className="stat-value">${(totalLeakage / 1000).toFixed(1)}K</div>
          <div className="stat-change negative">Live from Denial Analysis service</div>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Claims Trends</h3>
        </div>
        <div className="chart-container">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={chartData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="kpiCount" fill="#3b82f6" name="KPI Records" />
              <Bar dataKey="metricValue" fill="#10b981" name="Total Metric Value" />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Recent Claims</h3>
        </div>
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Claim ID</th>
                <th>Ingested Date</th>
                <th>Source</th>
                <th>Status</th>
                <th>Risk Score</th>
              </tr>
            </thead>
            <tbody>
              {pendingClaims.length > 0 ? (
                pendingClaims.slice(0, 8).map((claim) => {
                  const riskForClaim = highRisk.find((risk) => risk.claimId === claim.claimId)
                  return (
                    <tr key={claim.rawId}>
                      <td>{claim.claimId}</td>
                      <td>{claim.ingestedDate ? new Date(claim.ingestedDate).toLocaleDateString() : '-'}</td>
                      <td>{claim.sourceSystem || '-'}</td>
                      <td><span className="badge warning">{claim.processStatus || 'Pending'}</span></td>
                      <td>
                        <span className={`badge ${riskForClaim ? 'danger' : 'info'}`}>
                          {riskForClaim ? Number(riskForClaim.riskScore || 0).toFixed(2) : 'N/A'}
                        </span>
                      </td>
                    </tr>
                  )
                })
              ) : (
                <tr>
                  <td colSpan="5" style={{ textAlign: 'center' }}>
                    No live claim records available.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
