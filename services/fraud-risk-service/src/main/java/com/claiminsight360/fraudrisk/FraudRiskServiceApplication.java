package com.claiminsight360.fraudrisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FraudRiskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FraudRiskServiceApplication.class, args);
    }
}
