package com.claiminsight360.performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PerformanceAnalyticsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceAnalyticsServiceApplication.class, args);
    }
}
