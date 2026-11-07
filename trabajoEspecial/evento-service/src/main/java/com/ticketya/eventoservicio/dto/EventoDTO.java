package com.ticketya.eventoservicio.dto;

import com.ticketya.eventoservicio.models.EstadoEvento;

import java.time.LocalDateTime;
import java.util.List;

public class EventoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private List<String> participantes;
    private Long organizadorId;
    private LocalDateTime fechaHora;
    private LugarDTO lugar;
    private EstadoEvento estado;
    private List<SectorDTO> sectores;

    public EventoDTO(){}

    public EventoDTO(Long id, String nombre, String descripcion, String tipo, List<String> participantes, Long organizadorId, LocalDateTime fechaHora, LugarDTO lugar, EstadoEvento estado, List<SectorDTO> sectores) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.participantes = participantes;
        this.organizadorId = organizadorId;
        this.fechaHora = fechaHora;
        this.lugar = lugar;
        this.estado = estado;
        this.sectores = sectores;
    }

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

    public List<String> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<String> participantes) {
        this.participantes = participantes;
    }

    public Long getOrganizadorId() {
        return organizadorId;
    }

    public void setOrganizadorId(Long organizadorId) {
        this.organizadorId = organizadorId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public LugarDTO getLugar() {
        return lugar;
    }

    public void setLugar(LugarDTO lugar) {
        this.lugar = lugar;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    public List<SectorDTO> getSectores() {
        return sectores;
    }

    public void setSectores(List<SectorDTO> sectores) {
        this.sectores = sectores;
    }
}
