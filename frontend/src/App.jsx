import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom'
import { LayoutDashboard, Database, Shield, TrendingDown, LineChart, DollarSign, FileText, Bell } from 'lucide-react'
import Dashboard from './pages/Dashboard'
import DataIngestion from './pages/DataIngestion'
import FraudRisk from './pages/FraudRisk'
import DenialAnalysis from './pages/DenialAnalysis'
import Performance from './pages/Performance'
import CostReserve from './pages/CostReserve'
import Reports from './pages/Reports'
import Notifications from './pages/Notifications'

export default function App() {
  return (
    <Router>
      <div className="app-container">
        <aside className="sidebar">
          <div className="logo">
            <h1>ClaimInsight360</h1>
            <p>Insurance Claims Analytics</p>
          </div>
          
          <nav className="nav-menu">
            <NavLink to="/" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
              <LayoutDashboard size={20} />
              <span>Dashboard</span>
            </NavLink>
            
            <NavLink to="/data-ingestion" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
              <Database size={20} />
              <span>Data Ingestion</span>
            </NavLink>
            
            <NavLink to="/fraud-risk" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
              <Shield size={20} />
              <span>Fraud & Risk</span>
            </NavLink>
            
            <NavLink to="/denial-analysis" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
              <TrendingDown size={20} />
              <span>Denial Analysis</span>
            </NavLink>
            
            <NavLink to="/performance" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
              <LineChart size={20} />
              <span>Performance</span>
            </NavLink>
            
            <NavLink to="/cost-reserve" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
              <DollarSign size={20} />
              <span>Cost & Reserve</span>
            </NavLink>
            
            <NavLink to="/reports" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
              <FileText size={20} />
              <span>Reports</span>
            </NavLink>
            
            <NavLink to="/notifications" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
              <Bell size={20} />
              <span>Notifications</span>
            </NavLink>
          </nav>
        </aside>

        <main className="main-content">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/data-ingestion" element={<DataIngestion />} />
            <Route path="/fraud-risk" element={<FraudRisk />} />
            <Route path="/denial-analysis" element={<DenialAnalysis />} />
            <Route path="/performance" element={<Performance />} />
            <Route path="/cost-reserve" element={<CostReserve />} />
            <Route path="/reports" element={<Reports />} />
            <Route path="/notifications" element={<Notifications />} />
          </Routes>
        </main>
      </div>
    </Router>
  )
}
