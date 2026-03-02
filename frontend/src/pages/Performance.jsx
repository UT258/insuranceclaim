import { useState, useEffect } from 'react'
import { performanceAPI } from '../api'

export default function Performance() {
  const [violations, setViolations] = useState([])
  const [performance, setPerformance] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const [violationsRes, perfRes] = await Promise.all([
        performanceAPI.getUnresolvedViolations(),
        performanceAPI.getAllPerformance()
      ])
      setViolations(violationsRes.data)
      setPerformance(perfRes.data)
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
        <h2>Performance Analytics</h2>
        <p>Monitor adjuster productivity and SLA violations</p>
      </div>

      <div className="dashboard-grid">
        <div className="stat-card red">
          <div className="stat-label">SLA Violations</div>
          <div className="stat-value">{violations.length}</div>
        </div>
        <div className="stat-card blue">
          <div className="stat-label">Adjusters Tracked</div>
          <div className="stat-value">{performance.length}</div>
        </div>
        <div className="stat-card green">
          <div className="stat-label">Avg Quality Score</div>
          <div className="stat-value">85</div>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Adjuster Performance</h3>
        </div>
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Adjuster Name</th>
                <th>Claims Handled</th>
                <th>Avg TAT (days)</th>
                <th>Quality Score</th>
                <th>Period</th>
              </tr>
            </thead>
            <tbody>
              {performance.length > 0 ? (
                performance.map((perf) => (
                  <tr key={perf.perfId}>
                    <td>{perf.adjusterName}</td>
                    <td>{perf.claimsHandled}</td>
                    <td>{perf.avgTat?.toFixed(1) || '-'}</td>
                    <td>
                      <span className={`badge ${
                        perf.qualityScore >= 80 ? 'success' :
                        perf.qualityScore >= 60 ? 'warning' : 'danger'
                      }`}>
                        {perf.qualityScore?.toFixed(0) || '-'}
                      </span>
                    </td>
                    <td>{perf.periodValue}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5" style={{ textAlign: 'center' }}>
                    No performance records available.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Active SLA Violations</h3>
        </div>
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Violation ID</th>
                <th>Claim ID</th>
                <th>Type</th>
                <th>Assigned To</th>
                <th>Delay (days)</th>
                <th>Severity</th>
              </tr>
            </thead>
            <tbody>
              {violations.length > 0 ? (
                violations.map((violation) => (
                  <tr key={violation.violationId}>
                    <td>{violation.violationId}</td>
                    <td>{violation.claimId}</td>
                    <td>{violation.violationType}</td>
                    <td>{violation.assignedTo}</td>
                    <td>{violation.delayDays}</td>
                    <td>
                      <span className={`badge ${
                        violation.severity === 'Critical' ? 'danger' :
                        violation.severity === 'High' ? 'warning' : 'info'
                      }`}>
                        {violation.severity}
                      </span>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: 'center' }}>
                    No active SLA violations.
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
