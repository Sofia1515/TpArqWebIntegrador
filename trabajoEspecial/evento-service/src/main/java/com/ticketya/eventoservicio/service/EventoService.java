package com.ticketya.eventoservicio.service;

import com.ticketya.eventoservicio.dto.CrearEventoDTO;
import com.ticketya.eventoservicio.dto.EventoDTO;
import com.ticketya.eventoservicio.models.EstadoEvento;
import java.time.LocalDateTime;
import java.util.List;

public interface EventoService{
    EventoDTO crearEvento(CrearEventoDTO dto);

    EventoDTO obtenerPorId(Long id);

    List<EventoDTO> obtenerTodos();

    List<EventoDTO> obtenerPorIdOrganizador(Long idOrganizador);

    List<EventoDTO> obtenerPorCiudadYRangoDeFechas(String ciudad, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    EventoDTO cambiarEstado(Long id, EstadoEvento nuevoEstado);

    void eliminarEvento(Long id);
}