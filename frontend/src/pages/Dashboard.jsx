import { useEffect, useState } from 'react'
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts'

export default function Dashboard() {
  const [stats, setStats] = useState({
    totalClaims: 1250,
    pendingClaims: 320,
    highRiskClaims: 45,
    totalLeakage: 125000
  })

  return (
    <div>
      <div className="page-header">
        <h2>Dashboard</h2>
        <p>Overview of your claims analytics and key metrics</p>
      </div>

      <div className="dashboard-grid">
        <div className="stat-card blue">
          <div className="stat-label">Total Claims</div>
          <div className="stat-value">{stats.totalClaims}</div>
          <div className="stat-change positive">+12% from last month</div>
        </div>
        
        <div className="stat-card yellow">
          <div className="stat-label">Pending Claims</div>
          <div className="stat-value">{stats.pendingClaims}</div>
          <div className="stat-change positive">-5% from last month</div>
        </div>
        
        <div className="stat-card red">
          <div className="stat-label">High Risk Claims</div>
          <div className="stat-value">{stats.highRiskClaims}</div>
          <div className="stat-change negative">+8% from last month</div>
        </div>
        
        <div className="stat-card green">
          <div className="stat-label">Total Leakage</div>
          <div className="stat-value">${(stats.totalLeakage / 1000).toFixed(0)}K</div>
          <div className="stat-change negative">+3% from last month</div>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Claims Trends</h3>
        </div>
        <div className="chart-container">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={[
              { month: 'Jan', claims: 65, closed: 45 },
              { month: 'Feb', claims: 59, closed: 50 },
              { month: 'Mar', claims: 80, closed: 60 },
              { month: 'Apr', claims: 81, closed: 70 },
              { month: 'May', claims: 56, closed: 48 },
              { month: 'Jun', claims: 55, closed: 50 }
            ]}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="claims" fill="#3b82f6" />
              <Bar dataKey="closed" fill="#10b981" />
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
                <th>Date</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Risk Level</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>CLM-2024-001</td>
                <td>2024-03-02</td>
                <td>$12,500</td>
                <td><span className="badge success">Approved</span></td>
                <td><span className="badge info">Low</span></td>
              </tr>
              <tr>
                <td>CLM-2024-002</td>
                <td>2024-03-01</td>
                <td>$25,000</td>
                <td><span className="badge warning">Pending</span></td>
                <td><span className="badge warning">Medium</span></td>
              </tr>
              <tr>
                <td>CLM-2024-003</td>
                <td>2024-02-28</td>
                <td>$45,000</td>
                <td><span className="badge warning">Under Review</span></td>
                <td><span className="badge danger">High</span></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
