package com.order_query_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class OrderQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderQueryServiceApplication.class, args);
    }

}
