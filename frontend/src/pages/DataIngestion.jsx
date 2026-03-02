import { useState, useEffect } from 'react'
import { dataIngestionAPI } from '../api'

export default function DataIngestion() {
  const [feeds, setFeeds] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadFeeds()
  }, [])

  const loadFeeds = async () => {
    try {
      const response = await dataIngestionAPI.getAllFeeds()
      setFeeds(response.data)
    } catch (error) {
      console.error('Error loading feeds:', error)
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <div className="loading">Loading...</div>

  return (
    <div>
      <div className="page-header">
        <h2>Data Ingestion</h2>
        <p>Manage data feeds and claim data aggregation</p>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Active Data Feeds</h3>
          <button className="btn btn-primary">New Feed</button>
        </div>
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Feed ID</th>
                <th>Type</th>
                <th>Source System</th>
                <th>Last Sync</th>
                <th>Status</th>
                <th>Records</th>
              </tr>
            </thead>
            <tbody>
              {feeds.length > 0 ? (
                feeds.map((feed) => (
                  <tr key={feed.feedId}>
                    <td>{feed.feedId}</td>
                    <td>{feed.feedType}</td>
                    <td>{feed.sourceSystem}</td>
                    <td>{feed.lastSyncDate || 'Never'}</td>
                    <td>
                      <span className={`badge ${feed.status === 'Active' ? 'success' : 'warning'}`}>
                        {feed.status}
                      </span>
                    </td>
                    <td>{feed.recordsProcessed || 0}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: 'center' }}>
                    No data feeds available. Create your first feed to get started.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>

      <div className="dashboard-grid">
        <div className="stat-card blue">
          <div className="stat-label">Total Feeds</div>
          <div className="stat-value">{feeds.length}</div>
        </div>
        <div className="stat-card green">
          <div className="stat-label">Active Feeds</div>
          <div className="stat-value">{feeds.filter(f => f.status === 'Active').length}</div>
        </div>
        <div className="stat-card yellow">
          <div className="stat-label">Pending Claims</div>
          <div className="stat-value">0</div>
        </div>
      </div>
    </div>
  )
}
