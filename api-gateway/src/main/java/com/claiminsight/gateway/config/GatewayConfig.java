package com.claiminsight.gateway.config;

import com.claiminsight.gateway.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // IAM Service Routes
                .route("iam-service", r -> r
                        .path("/api/iam/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .circuitBreaker(config -> config
                                        .setName("iamCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/iam")))
                        .uri("lb://IAM-SERVICE"))

                // Data Ingestion Service Routes
                .route("data-ingestion-service", r -> r
                        .path("/api/data-ingestion/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config()))
                                .circuitBreaker(config -> config
                                        .setName("dataIngestionCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/data-ingestion")))
                        .uri("lb://DATA-INGESTION-SERVICE"))

                // Metrics Engine Service Routes
                .route("metrics-engine-service", r -> r
                        .path("/api/metrics/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config()))
                                .circuitBreaker(config -> config
                                        .setName("metricsCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/metrics")))
                        .uri("lb://METRICS-ENGINE-SERVICE"))

                // Fraud Risk Service Routes
                .route("fraud-risk-service", r -> r
                        .path("/api/fraud-risk/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config()))
                                .circuitBreaker(config -> config
                                        .setName("fraudRiskCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/fraud-risk")))
                        .uri("lb://FRAUD-RISK-SERVICE"))

                // Denial Analysis Service Routes
                .route("denial-analysis-service", r -> r
                        .path("/api/denial/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config()))
                                .circuitBreaker(config -> config
                                        .setName("denialCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/denial")))
                        .uri("lb://DENIAL-ANALYSIS-SERVICE"))

                // Adjuster Performance Service Routes
                .route("adjuster-performance-service", r -> r
                        .path("/api/adjuster/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config()))
                                .circuitBreaker(config -> config
                                        .setName("adjusterCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/adjuster")))
                        .uri("lb://ADJUSTER-PERFORMANCE-SERVICE"))

                // Cost Reserve Service Routes
                .route("cost-reserve-service", r -> r
                        .path("/api/cost-reserve/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config()))
                                .circuitBreaker(config -> config
                                        .setName("costReserveCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/cost-reserve")))
                        .uri("lb://COST-RESERVE-SERVICE"))

                // Dashboard Reports Service Routes
                .route("dashboard-reports-service", r -> r
                        .path("/api/dashboard/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config()))
                                .circuitBreaker(config -> config
                                        .setName("dashboardCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/dashboard")))
                        .uri("lb://DASHBOARD-REPORTS-SERVICE"))

                // Notification Service Routes
                .route("notification-service", r -> r
                        .path("/api/notifications/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config()))
                                .circuitBreaker(config -> config
                                        .setName("notificationCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/notification")))
                        .uri("lb://NOTIFICATION-SERVICE"))

                .build();
    }
}

