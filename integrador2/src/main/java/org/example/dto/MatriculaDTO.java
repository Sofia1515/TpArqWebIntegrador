package org.example.dto;

public class MatriculaDTO {
    private String nombreCarrera;
    private int anio;
    private long cantidadInscriptos;
    private long cantidadEgresados;

    public MatriculaDTO(String nombreCarrera, int anio, long cantidadInscriptos, long cantidadEgresados) {
        this.nombreCarrera = nombreCarrera;
        this.anio = anio;
        this.cantidadInscriptos = cantidadInscriptos;
        this.cantidadEgresados = cantidadEgresados;
    }

    public String getNombreCarrera() { return nombreCarrera; }
    public int getAnio() { return anio; }
    public long getCantidadInscriptos() { return cantidadInscriptos; }
    public long getCantidadEgresados() { return cantidadEgresados; }

    @Override
    public String toString() {
        return "Carrera: " + nombreCarrera + " | Año: " + anio +
                " | Inscriptos: " + cantidadInscriptos + " | Egresados: " + cantidadEgresados;
    }
}