package com.ticketya.usuarioservicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories(basePackages = "com.ticketya.usuarioservicio.repository")
@EnableMongoRepositories(basePackages = "com.ticketya.usuarioservicio.repository")
public class UsuarioServicioApplication {

    public static void main(String[]args){
        SpringApplication.run(UsuarioServicioApplication.class, args);
    }
}
