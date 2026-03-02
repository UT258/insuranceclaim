import { useState, useEffect } from 'react'
import { denialAnalysisAPI } from '../api'

export default function DenialAnalysis() {
  const [leakages, setLeakages] = useState([])
  const [totalLeakage, setTotalLeakage] = useState(0)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const [leakagesRes, totalRes] = await Promise.all([
        denialAnalysisAPI.getUninvestigatedLeakages(),
        denialAnalysisAPI.getTotalLeakageAmount()
      ])
      setLeakages(leakagesRes.data)
      setTotalLeakage(totalRes.data)
    } catch (error) {
      console.error('Error loading data:', error)
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <div className="loading">Loading...</div>

  return (
    <div>
      <div className="page-header">
        <h2>Denial & Leakage Analysis</h2>
        <p>Track denial patterns and financial leakages</p>
      </div>

      <div className="dashboard-grid">
        <div className="stat-card red">
          <div className="stat-label">Total Leakage Amount</div>
          <div className="stat-value">${(totalLeakage / 1000).toFixed(1)}K</div>
        </div>
        <div className="stat-card yellow">
          <div className="stat-label">Uninvestigated Leakages</div>
          <div className="stat-value">{leakages.length}</div>
        </div>
        <div className="stat-card blue">
          <div className="stat-label">Recovery Rate</div>
          <div className="stat-value">68%</div>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Leakage Flags</h3>
        </div>
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Leakage ID</th>
                <th>Claim ID</th>
                <th>Type</th>
                <th>Estimated Loss</th>
                <th>Identified Date</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {leakages.length > 0 ? (
                leakages.map((leakage) => (
                  <tr key={leakage.leakageId}>
                    <td>{leakage.leakageId}</td>
                    <td>{leakage.claimId}</td>
                    <td>{leakage.leakageType}</td>
                    <td>${leakage.estimatedLoss?.toFixed(2) || '0.00'}</td>
                    <td>{new Date(leakage.identifiedDate).toLocaleDateString()}</td>
                    <td>
                      <span className="badge warning">Uninvestigated</span>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: 'center' }}>
                    No uninvestigated leakages found.
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
