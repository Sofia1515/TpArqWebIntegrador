package org.example.dto;

public class EstudianteDTO {
    private String nombre;
    private String apellido;
    private int libretaUniversitaria;
    private String ciudadResidencia;
    private String genero;

    public EstudianteDTO(String nombre, String apellido, int libretaUniversitaria, String ciudadResidencia, String genero) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.libretaUniversitaria = libretaUniversitaria;
        this.ciudadResidencia = ciudadResidencia;
        this.genero = genero;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getLibretaUniversitaria() { return libretaUniversitaria; }
    public String getCiudadResidencia() { return ciudadResidencia; }
    public String getGenero() { return genero;}


    @Override
    public String toString() {
        return apellido + ", " + nombre + " | Libreta Universitaria: " + libretaUniversitaria + " | Ciudad: " + ciudadResidencia;
    }
}