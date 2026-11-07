package com.ticketya.eventoservicio.dto;

import com.ticketya.eventoservicio.models.EstadoEvento;
import com.ticketya.eventoservicio.models.Lugar;
import com.ticketya.eventoservicio.models.Sector;

import java.time.LocalDateTime;
import java.util.List;

public class CrearEventoDTO {
    private String nombre;
    private String descripcion;
    private String tipo;
    private Long organizadorId;
    private List<String> participantes;
    private LocalDateTime fechaHora;
    private Long idLugar;
    private List<SectorDTO> sectores;

   public CrearEventoDTO(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getOrganizadorId() {
        return organizadorId;
    }

    public void setOrganizadorId(Long organizadorId) {
        this.organizadorId = organizadorId;
    }

    public List<String> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<String> participantes) {
        this.participantes = participantes;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Long getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(Long idLugar) {
        this.idLugar = idLugar;
    }

    public List<SectorDTO> getSectores() {
        return sectores;
    }

    public void setSectores(List<SectorDTO> sectores) {
        this.sectores = sectores;
    }
}
