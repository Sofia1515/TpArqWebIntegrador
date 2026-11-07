package com.ticketya.eventoservicio.repository;

import com.ticketya.eventoservicio.models.EstadoEvento;
import com.ticketya.eventoservicio.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // buscar eventos por tipo de evento
    List<Evento> findByTipoIgnoreCase(String tipo);

    // buscar eventos por idOrganizador
    List<Evento> findByOrganizadorId(Long organizadorId);

    // buscar eventos por estado(publicado)
    List<Evento> findByEstado(EstadoEvento estado);

    /**
     * FILTRO ESPECÍFICO: Buscar por Ciudad y Rango de Fechas
     * Permite buscar por ciudad (o todas las ciudades si ciudad es null)
     * dentro de una fecha inicio y fecha fin y ordenados.
     */
    @Query("SELECT e FROM Evento e " +
            "WHERE (:ciudad IS NULL OR LOWER(e.lugar.ciudad) = LOWER(:ciudad)) " +
            "AND e.fechaHora >= :fechaInicio " +
            "AND e.fechaHora <= :fechaFin " +
            "ORDER BY e.fechaHora ASC")
    List<Evento> buscarPorCiudadYRangoFechas(
            @Param("ciudad") String ciudad,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
}