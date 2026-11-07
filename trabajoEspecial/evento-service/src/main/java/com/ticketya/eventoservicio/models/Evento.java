package com.ticketya.eventoservicio.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "id_organizador", nullable = false)
    private Long organizadorId;

    @ElementCollection
    @CollectionTable(name = "evento_participante", joinColumns = @JoinColumn(name = "id_evento"))
    @Column(name = "participante")
    private List<String> participantes = new ArrayList<>();

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lugar", referencedColumnName = "id", nullable = false)
    private Lugar lugar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEvento estado;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sector> sectores = new ArrayList<>();

    public Evento() {}

    public Evento(String nombre, String descripcion, String tipo, Long organizadorId, List<String> participantes, LocalDateTime fechaHora, Lugar lugar, EstadoEvento estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.organizadorId = organizadorId;
        this.participantes = participantes;
        this.fechaHora = fechaHora;
        this.lugar = lugar;
        this.estado = estado;
    }

    // metodos helper
    public void agregarSector(Sector sector){
        sectores.add(sector);
        sector.setEvento(this);
    }

    public void borrarSector(Sector sector){
        sectores.remove(sector);
        sector.setEvento(null);
    }

    //getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    public List<Sector> getSectores() {
        return sectores;
    }

    public void setSectores(List<Sector> sectores) {
        this.sectores = sectores;
    }

    public List<String> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<String> participantes) {
        this.participantes = participantes;
    }
}