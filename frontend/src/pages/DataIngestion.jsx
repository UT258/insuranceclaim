import { useState, useEffect } from 'react'
import { dataIngestionAPI } from '../api'

export default function DataIngestion() {
  const [feeds, setFeeds] = useState([])
  const [loading, setLoading] = useState(true)
  const [creating, setCreating] = useState(false)
  const [formData, setFormData] = useState({
    feedType: 'Claim',
    sourceSystem: '',
    status: 'Active'
  })

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

  const handleCreateFeed = async (event) => {
    event.preventDefault()
    try {
      await dataIngestionAPI.createFeed(formData)
      setFormData({ feedType: 'Claim', sourceSystem: '', status: 'Active' })
      setCreating(false)
      loadFeeds()
    } catch (error) {
      console.error('Error creating feed:', error)
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
          <button className="btn btn-primary" onClick={() => setCreating((value) => !value)}>
            {creating ? 'Cancel' : 'New Feed'}
          </button>
        </div>

        {creating && (
          <form onSubmit={handleCreateFeed} style={{ padding: '16px 24px', borderBottom: '1px solid #e2e8f0' }}>
            <div className="dashboard-grid" style={{ marginBottom: 12 }}>
              <div>
                <label>Feed Type</label>
                <input
                  value={formData.feedType}
                  onChange={(event) => setFormData((prev) => ({ ...prev, feedType: event.target.value }))}
                  required
                />
              </div>
              <div>
                <label>Source System</label>
                <input
                  value={formData.sourceSystem}
                  onChange={(event) => setFormData((prev) => ({ ...prev, sourceSystem: event.target.value }))}
                  required
                />
              </div>
              <div>
                <label>Status</label>
                <input
                  value={formData.status}
                  onChange={(event) => setFormData((prev) => ({ ...prev, status: event.target.value }))}
                  required
                />
              </div>
            </div>
            <button className="btn btn-primary" type="submit">Create Feed</button>
          </form>
        )}
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
