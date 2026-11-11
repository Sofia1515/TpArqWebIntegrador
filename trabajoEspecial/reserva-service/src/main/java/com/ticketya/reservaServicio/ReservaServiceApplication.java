package com.ticketya.reservaServicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ticketya.reservaServicio.cliente")
@EnableJpaRepositories(basePackages = "com.ticketya.reservaServicio.repository")
@EntityScan(basePackages = "com.ticketya.reservaServicio.models")
public class ReservaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservaServiceApplication.class, args);
    }
}