package com.ticketya.reservaServicio.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CompraDTO {
    private Long id;
    private Long idUsuario;
    private Long idEvento;
    private Double montoTotal;
    private String medioDePago;
    private LocalDateTime fechaCompra;
    private List<EntradaDTO> entradas;

    public CompraDTO() {}

    public CompraDTO(Long id, Long idUsuario, Long idEvento, Double montoTotal, String medioDePago, LocalDateTime fechaCompra, List<EntradaDTO> entradas) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.montoTotal = montoTotal;
        this.medioDePago = medioDePago;
        this.fechaCompra = fechaCompra;
        this.entradas = entradas;
    }

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdEvento() { return idEvento; }
    public void setIdEvento(Long idEvento) { this.idEvento = idEvento; }
    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }
    public String getMedioDePago() { return medioDePago; }
    public void setMedioDePago(String medioDePago) { this.medioDePago = medioDePago; }
    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }
    public List<EntradaDTO> getEntradas() { return entradas; }
    public void setEntradas(List<EntradaDTO> entradas) { this.entradas = entradas; }
}
