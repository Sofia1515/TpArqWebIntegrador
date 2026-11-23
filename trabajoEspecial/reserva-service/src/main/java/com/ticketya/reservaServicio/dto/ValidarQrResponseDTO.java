package com.ticketya.reservaServicio.dto;

public class ValidarQrResponseDTO {
    private boolean valida;
    private String mensaje;
    private Long idEntrada;
    private Long idSector;
    private Integer fila;
    private Integer asiento;

    public ValidarQrResponseDTO() {}

    public ValidarQrResponseDTO(boolean valida, String mensaje, Long idEntrada, Long idSector, Integer fila, Integer asiento) {
        this.valida = valida;
        this.mensaje = mensaje;
        this.idEntrada = idEntrada;
        this.idSector = idSector;
        this.fila = fila;
        this.asiento = asiento;
    }

    public boolean isValida() { return valida; }
    public String getMensaje() { return mensaje; }
    public Long getIdEntrada() { return idEntrada; }
    public Long getIdSector() { return idSector; }
    public Integer getFila() { return fila; }
    public Integer getAsiento() { return asiento; }
}