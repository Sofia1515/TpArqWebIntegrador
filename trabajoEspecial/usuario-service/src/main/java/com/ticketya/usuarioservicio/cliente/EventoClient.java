package com.ticketya.usuarioservicio.cliente;

import com.ticketya.usuarioservicio.dto.EventoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "evento-servicio", url = "http://localhost:8082/api/eventos")
public interface EventoClient {

    @GetMapping
    List<EventoDTO> obtenerTodosLosEventos();
}
