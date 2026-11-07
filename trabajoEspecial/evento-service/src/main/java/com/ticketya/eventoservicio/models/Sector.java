package com.ticketya.eventoservicio.models;

import jakarta.persistence.*;

@Entity
@Table(name= "sector")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean esNumerado;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private int capacidadMaxima;

    @Column(name = "filas")
    private int filas;

    @Column(name = "asientos_por_fila")
    private int asientosPorFila;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    public Sector(){}

    public Sector(String nombre, boolean esNumerado, double precio, int capacidadMaxima, Evento evento) {
        this.nombre = nombre;
        this.esNumerado = esNumerado;
        this.precio = precio;
        this.capacidadMaxima = capacidadMaxima;
        this.evento = evento;
    }

    //getters y setters

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
        return this.esNumerado;
    }

    public void setEsNumerado(boolean esNumerado) {
        this.esNumerado = esNumerado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
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

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}