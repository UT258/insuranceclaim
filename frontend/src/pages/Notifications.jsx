import { useState, useEffect } from 'react'
import { notificationAPI } from '../api'

export default function Notifications() {
  const [notifications, setNotifications] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadNotifications()
  }, [])

  const loadNotifications = async () => {
    try {
      const response = await notificationAPI.getAllNotifications()
      setNotifications(response.data)
    } catch (error) {
      console.error('Error loading notifications:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleMarkAsRead = async (id) => {
    try {
      await notificationAPI.markAsRead(id)
      loadNotifications()
    } catch (error) {
      console.error('Error marking as read:', error)
    }
  }

  if (loading) return <div className="loading">Loading...</div>

  return (
    <div>
      <div className="page-header">
        <h2>Notifications & Alerts</h2>
        <p>Stay updated with important alerts and system notifications</p>
      </div>

      <div className="dashboard-grid">
        <div className="stat-card blue">
          <div className="stat-label">Total Notifications</div>
          <div className="stat-value">{notifications.length}</div>
        </div>
        <div className="stat-card yellow">
          <div className="stat-label">Unread</div>
          <div className="stat-value">{notifications.filter(n => n.status === 'Unread').length}</div>
        </div>
        <div className="stat-card green">
          <div className="stat-label">Read</div>
          <div className="stat-value">{notifications.filter(n => n.status === 'Read').length}</div>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">All Notifications</h3>
        </div>
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Category</th>
                <th>Message</th>
                <th>Created Date</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {notifications.length > 0 ? (
                notifications.map((notification) => (
                  <tr key={notification.notificationId}>
                    <td>{notification.notificationId}</td>
                    <td>
                      <span className={`badge ${
                        notification.category === 'Risk' ? 'danger' :
                        notification.category === 'Denial' ? 'warning' :
                        notification.category === 'Cost' ? 'info' : 'success'
                      }`}>
                        {notification.category}
                      </span>
                    </td>
                    <td>{notification.message}</td>
                    <td>{new Date(notification.createdDate).toLocaleDateString()}</td>
                    <td>
                      <span className={`badge ${
                        notification.status === 'Unread' ? 'warning' : 'success'
                      }`}>
                        {notification.status}
                      </span>
                    </td>
                    <td>
                      {notification.status === 'Unread' && (
                        <button 
                          className="btn btn-secondary" 
                          style={{ fontSize: '12px', padding: '6px 12px' }}
                          onClick={() => handleMarkAsRead(notification.notificationId)}
                        >
                          Mark Read
                        </button>
                      )}
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: 'center' }}>
                    No notifications available.
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
