package com.ticketya.reservaServicio.cliente;

import com.ticketya.reservaServicio.dto.EventoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "evento-servicio", url = "http://localhost:8082")
public interface EventoClient {

    @GetMapping("/api/eventos/{id}")
    EventoDTO obtenerEventoPorId(@PathVariable("id") Long id);
}