package com.ticketya.reservaServicio.dto;

import java.util.List;

public class EventoDTO{
    private Long id;
    private String nombre;
    private String estado; // BORRADOR, PUBLICADO, AGOTADO, CANCELADO
    private List<SectorDTO> sectores;

    public EventoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<SectorDTO> getSectores() { return sectores; }
    public void setSectores(List<SectorDTO> sectores) { this.sectores = sectores; }
}