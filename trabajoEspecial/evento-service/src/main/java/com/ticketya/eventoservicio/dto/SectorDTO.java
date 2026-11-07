package com.ticketya.eventoservicio.dto;

public class SectorDTO {
    private Long id;
    private String nombre;
    private boolean esNumerado;
    private int capacidadMaxima;
    private double precio;
    private int filas;
    private int asientosPorFila;

    public SectorDTO(Long id, String nombre, boolean esNumerado, double precio, int capacidadMaxima, int filas, int asientosPorFila){}

    public SectorDTO(String nombre, Long id, boolean esNumerado, int capacidadMaxima, double precio, int filas, int asientosPorFila) {
        this.nombre = nombre;
        this.id = id;
        this.esNumerado = esNumerado;
        this.capacidadMaxima = capacidadMaxima;
        this.precio = precio;
        this.filas = filas;
        this.asientosPorFila = asientosPorFila;
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

    public boolean getEsNumerado() {
        return esNumerado;
    }

    public void setEsNumerado(boolean esNumerado) {
        this.esNumerado = esNumerado;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getAsientosPorFila() {
        return asientosPorFila;
    }

    public void setAsientosPorFila(int asientosPorFila) {
        this.asientosPorFila = asientosPorFila;
    }


}
