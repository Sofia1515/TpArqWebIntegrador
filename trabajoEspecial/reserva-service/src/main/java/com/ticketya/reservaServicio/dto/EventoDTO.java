
package com.ticketya.reservaServicio.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EventoDTO {
    private Long id;
    private String nombre;
    private String estado;
    private LocalDateTime fechaHora;
    private List<SectorDTO> sectores;
    private boolean altaDemanda;

    public EventoDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public List<SectorDTO> getSectores() {
        return sectores;
    }

    public void setSectores(List<SectorDTO> sectores) {
        this.sectores = sectores;
    }

    public boolean isAltaDemanda() {
        return altaDemanda;
    }

    public void setAltaDemanda(boolean altaDemanda) {
        this.altaDemanda = altaDemanda;
    }
}
