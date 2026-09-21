package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "matricula")
public class Matricula {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "inscripcion")
    private int inscripcion;

    @Column(name = "graduacion")
    private int graduacion;

    @Column(name = "antiguedad")
    private int antiguedad;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_estudiante", referencedColumnName = "dni")
    private Estudiante estudiante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_carrera", referencedColumnName = "id_carrera")
    private Carrera carrera;

    public Matricula() {}

    public Matricula(int id, int inscripcion, int graduacion, int antiguedad, Estudiante estudiante, Carrera carrera) {
        this.id = id;
        this.inscripcion = inscripcion;
        this.graduacion = graduacion;
        this.antiguedad = antiguedad;
        this.estudiante = estudiante;
        this.carrera = carrera;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getInscripcion() { return inscripcion; }
    public void setInscripcion(int inscripcion) { this.inscripcion = inscripcion; }

    public int getGraduacion() { return graduacion; }
    public void setGraduacion(int graduacion) { this.graduacion = graduacion; }

    public int getAntiguedad() { return antiguedad; }
    public void setAntiguedad(int antiguedad) { this.antiguedad = antiguedad; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public Carrera getCarrera() { return carrera; }
    public void setCarrera(Carrera carrera) { this.carrera = carrera; }

    // Método helper para saber si está graduado en los reportes
    public boolean isGraduado() {
        return this.graduacion > 0;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", inscripcion=" + inscripcion +
                ", graduacion=" + graduacion +
                ", antiguedad=" + antiguedad +
                ", estudiante=" + (estudiante != null ? estudiante.getNombre() : "null") +
                ", carrera=" + (carrera != null ? carrera.getNombre() : "null") +
                '}';
    }
}