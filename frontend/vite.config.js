import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
    host: '0.0.0.0',
    port: 3000,
    proxy: {
      '/api/data-ingestion': {
        target: 'http://data-ingestion-service:8085',
        changeOrigin: true
      },
      '/api/fraud-risk': {
        target: 'http://fraud-risk-service:8086',
        changeOrigin: true
      },
      '/api/denial-analysis': {
        target: 'http://denial-analysis-service:8087',
        changeOrigin: true
      },
      '/api/performance': {
        target: 'http://performance-analytics-service:8088',
        changeOrigin: true
      },
      '/api/cost-reserve': {
        target: 'http://cost-reserve-service:8089',
        changeOrigin: true
      },
      '/api/dashboard': {
        target: 'http://dashboard-service:8090',
        changeOrigin: true
      },
      '/api/notifications': {
        target: 'http://notification-service:8084',
        changeOrigin: true,
        rewrite: (path) => {
          if (/^\/api\/notifications\/\d+\/read$/.test(path)) {
            return path.replace(/^\/api\/notifications\/(\d+)\/read$/, '/api/v1/notifications/$1')
          }
          return path.replace(/^\/api\/notifications/, '/api/v1/notifications')
        }
      },
      '/api/identity': {
        target: 'http://identity-service:8082',
        changeOrigin: true,
        rewrite: () => '/api/v1/users'
      },
      '/api/analytics': {
        target: 'http://analytics-service:8083',
        changeOrigin: true,
        rewrite: () => '/api/v1/kpis'
      },
      '/api/claims/dashboard': {
        target: 'http://claims-service:8081',
        changeOrigin: true,
        rewrite: () => '/api/v1/dashboard/summary'
      }
    }
  }
});
