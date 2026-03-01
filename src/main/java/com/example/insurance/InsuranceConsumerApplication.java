package com.example.insurance;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InsuranceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceConsumerApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(InsurancePlanService service) {
        return args -> {
            service.consumeAllPlans();
        };
    }
}
