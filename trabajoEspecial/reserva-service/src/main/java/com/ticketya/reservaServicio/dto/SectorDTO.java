package com.ticketya.reservaServicio.dto;

public class SectorDTO {
    private Long id;
    private String nombre;
    private Boolean esNumerado;
    private Double precio;
    private Integer capacidad;

    public SectorDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Boolean getEsNumerado() { return esNumerado; }
    public void setEsNumerado(Boolean esNumerado) { this.esNumerado = esNumerado; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
}
