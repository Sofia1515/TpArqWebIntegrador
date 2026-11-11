package com.ticketya.reservaServicio.dto;

import com.ticketya.reservaServicio.models.EstadoEntrada;

public class EntradaDTO {
    private Long id;
    private Long idSector;
    private Integer fila;
    private Integer asiento;
    private String codigoQR;
    private EstadoEntrada estadoEntrada;

    public EntradaDTO() {}

    public EntradaDTO(Long id, Long idSector, Integer fila, Integer asiento, String codigoQR, EstadoEntrada estadoEntrada) {
        this.id = id;
        this.idSector = idSector;
        this.fila = fila;
        this.asiento = asiento;
        this.codigoQR = codigoQR;
        this.estadoEntrada = estadoEntrada;
    }

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdSector() { return idSector; }
    public void setIdSector(Long idSector) { this.idSector = idSector; }
    public Integer getFila() { return fila; }
    public void setFila(Integer fila) { this.fila = fila; }
    public Integer getAsiento() { return asiento; }
    public void setAsiento(Integer asiento) { this.asiento = asiento; }
    public String getCodigoQR() { return codigoQR; }
    public void setCodigoQR(String codigoQR) { this.codigoQR = codigoQR; }
    public EstadoEntrada getEstadoEntrada() { return estadoEntrada; }
    public void setEstadoEntrada(EstadoEntrada estadoEntrada) { this.estadoEntrada = estadoEntrada; }
}
