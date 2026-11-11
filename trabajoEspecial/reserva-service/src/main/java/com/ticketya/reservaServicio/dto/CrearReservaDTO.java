package com.ticketya.reservaServicio.dto;

public class CrearReservaDTO {

    private Long idUsuario;
    private Long idEvento;
    private Long idSector;
    private int fila;
    private int asiento;

    public CrearReservaDTO() {}

    public CrearReservaDTO(Long idUsuario, Long idEvento, Long idSector, int fila, int asiento) {
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.idSector = idSector;
        this.fila = fila;
        this.asiento = asiento;
    }

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
}

