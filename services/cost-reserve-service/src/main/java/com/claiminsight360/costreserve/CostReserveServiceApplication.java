package com.claiminsight360.costreserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CostReserveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CostReserveServiceApplication.class, args);
    }
}
