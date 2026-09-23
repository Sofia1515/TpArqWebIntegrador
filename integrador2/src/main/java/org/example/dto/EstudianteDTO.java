package org.example.dto;

public class EstudianteDTO {
    private String nombre;
    private String apellido;
    private int libretaUniversitaria;
    private String ciudadResidencia;

    public EstudianteDTO(String nombre, String apellido, int libretaUniversitaria, String ciudadResidencia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.libretaUniversitaria = libretaUniversitaria;
        this.ciudadResidencia = ciudadResidencia;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getLibretaUniversitaria() { return libretaUniversitaria; }
    public String getCiudadResidencia() { return ciudadResidencia; }

    @Override
    public String toString() {
        return apellido + ", " + nombre + " | Libreta Universitaria: " + libretaUniversitaria + " | Ciudad: " + ciudadResidencia;
    }
}