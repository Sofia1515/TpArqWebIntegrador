package com.ticketya.reservaServicio.dto;

import com.ticketya.reservaServicio.models.EstadoReserva;

import java.time.LocalDateTime;

public class ReservaDTO {
    private Long id;
    private Long idUsuario;
    private Long idEvento;
    private Long idSector;
    private Integer fila;
    private Integer asiento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaVencimiento;
    private EstadoReserva estadoReserva;

    public ReservaDTO() {}

    public ReservaDTO(Long id, Long idUsuario, Long idEvento, Long idSector, int fila, int asiento, LocalDateTime fechaCreacion, LocalDateTime fechaVencimiento, EstadoReserva estadoReserva) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.idSector = idSector;
        this.fila = fila;
        this.asiento = asiento;
        this.fechaCreacion = fechaCreacion;
        this.fechaVencimiento = fechaVencimiento;
        this.estadoReserva = estadoReserva;
    }

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdEvento() { return idEvento; }
    public void setIdEvento(Long idEvento) { this.idEvento = idEvento; }
    public Long getIdSector() { return idSector; }
    public void setIdSector(Long idSector) { this.idSector = idSector; }
    public Integer getFila() { return fila; }
    public void setFila(Integer fila) { this.fila = fila; }
    public Integer getAsiento() { return asiento; }
    public void setAsiento(Integer asiento) { this.asiento = asiento; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public EstadoReserva getEstadoReserva() { return estadoReserva; }
    public void setEstadoReserva(EstadoReserva estadoReserva) { this.estadoReserva = estadoReserva; }
}
