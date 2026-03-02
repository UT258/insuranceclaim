import { useState, useEffect } from 'react'
import { costReserveAPI } from '../api'

export default function CostReserve() {
  const [staleClaims, setStaleClaims] = useState([])
  const [totalAgingDays, setTotalAgingDays] = useState(0)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const response = await costReserveAPI.getStaleClaims()
      const rows = response.data || []
      setStaleClaims(rows)
      setTotalAgingDays(rows.reduce((sum, row) => sum + Number(row.agingDays || 0), 0))
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
          <div className="stat-label">Stale Claim Records</div>
          <div className="stat-value">{staleClaims.length}</div>
        </div>
        <div className="stat-card green">
          <div className="stat-label">Avg Aging Days</div>
          <div className="stat-value">
            {staleClaims.length > 0 ? (totalAgingDays / staleClaims.length).toFixed(1) : '0.0'}
          </div>
        </div>
        <div className="stat-card yellow">
          <div className="stat-label">High/Urgent Priority</div>
          <div className="stat-value">
            {staleClaims.filter((row) => ['HIGH', 'URGENT'].includes((row.priority || '').toUpperCase())).length}
          </div>
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
          <h3 className="card-title">Aging Bucket Distribution (Live)</h3>
        </div>
        <div className="dashboard-grid">
          {[...new Set(staleClaims.map((row) => row.agingBucket || 'Unknown'))].map((bucket) => (
            <div className="stat-card blue" key={bucket}>
              <div className="stat-label">{bucket}</div>
              <div className="stat-value">{staleClaims.filter((row) => (row.agingBucket || 'Unknown') === bucket).length}</div>
            </div>
          ))}
          {staleClaims.length === 0 && (
            <div className="stat-card blue">
              <div className="stat-label">No Data</div>
              <div className="stat-value">0</div>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}
