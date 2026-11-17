package com.ticketya.reservaServicio.dto;

public class ValidarQrRequestDTO {
    private String codigoQR;
    private Long idEvento;

    public ValidarQrRequestDTO() {}
    public String getCodigoQR() { return codigoQR; }
    public void setCodigoQR(String codigoQR) { this.codigoQR = codigoQR; }
    public Long getIdEvento() { return idEvento; }
    public void setIdEvento(Long idEvento) { this.idEvento = idEvento; }
}
