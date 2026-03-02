import { useState, useEffect } from 'react'
import { costReserveAPI } from '../api'

export default function CostReserve() {
  const [staleClaims, setStaleClaims] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const response = await costReserveAPI.getStaleClaims()
      setStaleClaims(response.data)
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
        <h2>Cost & Reserve Analytics</h2>
        <p>Track claim costs, reserves, and aging analysis</p>
      </div>

      <div className="dashboard-grid">
        <div className="stat-card blue">
          <div className="stat-label">Total Reserves</div>
          <div className="stat-value">$2.5M</div>
        </div>
        <div className="stat-card green">
          <div className="stat-label">Paid Claims</div>
          <div className="stat-value">$1.8M</div>
        </div>
        <div className="stat-card yellow">
          <div className="stat-label">Remaining Reserve</div>
          <div className="stat-value">$700K</div>
        </div>
        <div className="stat-card red">
          <div className="stat-label">Stale Claims</div>
          <div className="stat-value">{staleClaims.length}</div>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Aging Analysis</h3>
        </div>
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Aging ID</th>
                <th>Claim ID</th>
                <th>Aging Days</th>
                <th>Aging Bucket</th>
                <th>Status</th>
                <th>Assigned To</th>
                <th>Priority</th>
              </tr>
            </thead>
            <tbody>
              {staleClaims.length > 0 ? (
                staleClaims.map((aging) => (
                  <tr key={aging.agingId}>
                    <td>{aging.agingId}</td>
                    <td>{aging.claimId}</td>
                    <td>{aging.agingDays}</td>
                    <td>
                      <span className="badge warning">{aging.agingBucket}</span>
                    </td>
                    <td>{aging.claimStatus}</td>
                    <td>{aging.assignedAdjuster || 'Unassigned'}</td>
                    <td>
                      <span className={`badge ${
                        aging.priority === 'Urgent' ? 'danger' :
                        aging.priority === 'High' ? 'warning' : 'info'
                      }`}>
                        {aging.priority}
                      </span>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="7" style={{ textAlign: 'center' }}>
                    No stale claims found.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Cost Distribution</h3>
        </div>
        <div className="dashboard-grid">
          <div className="stat-card blue">
            <div className="stat-label">Medical Costs</div>
            <div className="stat-value">$850K</div>
          </div>
          <div className="stat-card green">
            <div className="stat-label">Legal Costs</div>
            <div className="stat-value">$320K</div>
          </div>
          <div className="stat-card yellow">
            <div className="stat-label">Repair Costs</div>
            <div className="stat-value">$480K</div>
          </div>
          <div className="stat-card red">
            <div className="stat-label">Administrative</div>
            <div className="stat-value">$150K</div>
          </div>
        </div>
      </div>
    </div>
  )
}
