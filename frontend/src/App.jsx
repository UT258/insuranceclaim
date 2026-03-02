import { useEffect, useState } from "react";

const gatewayBaseUrl = "http://localhost:8080/api/v1";

async function getJson(path) {
  const response = await fetch(`${gatewayBaseUrl}${path}`);
  if (!response.ok) {
    throw new Error(`Request failed: ${response.status}`);
  }
  return response.json();
}

export default function App() {
  const [dashboard, setDashboard] = useState(null);
  const [users, setUsers] = useState([]);
  const [kpis, setKpis] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [trends, setTrends] = useState(null);
  const [riskSummary, setRiskSummary] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const load = async () => {
      try {
        const [dashboardData, usersData, kpisData, notificationsData, trendsData, riskData] = await Promise.all([
          getJson("/claims/dashboard").catch(() => ({})),
          getJson("/identity/users").catch(() => []),
          getJson("/analytics/kpis").catch(() => []),
          getJson("/notifications").catch(() => []),
          getJson("/analytics/trends").catch(() => ({})),
          getJson("/analytics/risk-summary").catch(() => ({}))
        ]);
        setDashboard(dashboardData);
        setUsers(usersData);
        setKpis(kpisData);
        setNotifications(notificationsData);
        setTrends(trendsData);
        setRiskSummary(riskData);
      } catch (e) {
        setError(e.message);
      } finally {
        setLoading(false);
      }
    };

    load();
  }, []);

  if (loading) return <main className="container"><p>Loading ClaimInsight360...</p></main>;

  return (
    <main className="container">
      <header>
        <h1>📊 ClaimInsight360</h1>
        <p>Insurance Claims Analytics & Intelligence Platform</p>
      </header>

      {error && <p className="error">⚠️ {error}</p>}

      <section className="grid">
        <article className="card">
          <h2>📋 Claims Summary</h2>
          <p><strong>Data Feeds:</strong> {dashboard?.totalFeeds ?? 0}</p>
          <p><strong>Raw Claims:</strong> {dashboard?.totalRawClaims ?? 0}</p>
          <p><strong>Risk Scores:</strong> {dashboard?.totalRiskScores ?? 0}</p>
          <p><strong>SLA Violations:</strong> {dashboard?.totalSlaViolations ?? 0}</p>
        </article>

        <article className="card">
          <h2>💰 Financial Overview</h2>
          <p><strong>Total Claim Cost:</strong> ${(dashboard?.totalClaimCost ?? 0).toLocaleString()}</p>
          <p><strong>Total Reserve:</strong> ${(dashboard?.totalReserveAmount ?? 0).toLocaleString()}</p>
        </article>

        <article className="card">
          <h2>📈 Trends</h2>
          <p><strong>Period:</strong> {trends?.period ?? "N/A"}</p>
          <p><strong>Claim Volume:</strong> {trends?.claimVolume ?? 0}</p>
          <p><strong>Denial Rate:</strong> {(trends?.denialRate * 100)?.toFixed(1) ?? 0}%</p>
        </article>

        <article className="card">
          <h2>⚠️ Risk Analysis</h2>
          <p><strong>High-Risk Claims:</strong> {riskSummary?.highRiskClaims ?? 0}</p>
          <p><strong>Anomalies Detected:</strong> {riskSummary?.anomaliesDetected ?? 0}</p>
          <p><strong>Avg Risk Score:</strong> {riskSummary?.avgRiskScore ?? 0}</p>
        </article>

        <article className="card">
          <h2>👥 Users</h2>
          <p><strong>Total Users:</strong> {users.length}</p>
          {users.slice(0, 2).map((u, i) => (
            <p key={i}>{u.name} ({u.role})</p>
          ))}
        </article>

        <article className="card">
          <h2>📊 KPIs</h2>
          <p><strong>Metrics:</strong> {kpis.length}</p>
          {kpis.slice(0, 2).map((k, i) => (
            <p key={i}>{k.metricName}: {k.metricValue}</p>
          ))}
        </article>

        <article className="card">
          <h2>🔔 Notifications</h2>
          <p><strong>Alerts:</strong> {notifications.length}</p>
          {notifications.slice(0, 2).map((n, i) => (
            <p key={i} className="notification">{n.category}: {n.message.substring(0, 30)}...</p>
          ))}
        </article>
      </section>
    </main>
  );
}
