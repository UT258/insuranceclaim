import { useState, useEffect } from 'react'
import { fraudRiskAPI } from '../api'

export default function FraudRisk() {
  const [indicators, setIndicators] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadIndicators()
  }, [])

  const loadIndicators = async () => {
    try {
      const response = await fraudRiskAPI.getUnresolvedIndicators()
      setIndicators(response.data)
    } catch (error) {
      console.error('Error loading indicators:', error)
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <div className="loading">Loading...</div>

  return (
    <div>
      <div className="page-header">
        <h2>Fraud & Risk Management</h2>
        <p>Monitor risk indicators and fraud detection</p>
      </div>

      <div className="dashboard-grid">
        <div className="stat-card red">
          <div className="stat-label">Critical Risks</div>
          <div className="stat-value">{indicators.filter(i => i.severity === 'Critical').length}</div>
        </div>
        <div className="stat-card yellow">
          <div className="stat-label">High Risks</div>
          <div className="stat-value">{indicators.filter(i => i.severity === 'High').length}</div>
        </div>
        <div className="stat-card blue">
          <div className="stat-label">Total Indicators</div>
          <div className="stat-value">{indicators.length}</div>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Active Risk Indicators</h3>
        </div>
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Indicator ID</th>
                <th>Claim ID</th>
                <th>Type</th>
                <th>Severity</th>
                <th>Triggered Date</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              {indicators.length > 0 ? (
                indicators.map((indicator) => (
                  <tr key={indicator.indicatorId}>
                    <td>{indicator.indicatorId}</td>
                    <td>{indicator.claimId}</td>
                    <td>{indicator.indicatorType}</td>
                    <td>
                      <span className={`badge ${
                        indicator.severity === 'Critical' ? 'danger' :
                        indicator.severity === 'High' ? 'warning' : 'info'
                      }`}>
                        {indicator.severity}
                      </span>
                    </td>
                    <td>{new Date(indicator.triggeredDate).toLocaleDateString()}</td>
                    <td>{indicator.description || '-'}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: 'center' }}>
                    No active risk indicators found.
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
