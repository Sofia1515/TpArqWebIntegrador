package org.example.dto;

public class ReporteCarreraDTO {

    private String carrera;
    private int anio;
    private long inscriptos;

    public long getEgresados() {
        return egresados;
    }

    public String getCarrera() {
        return carrera;
    }

    public int getAnio() {
        return anio;
    }

    public long getInscriptos() {
        return inscriptos;
    }

    private long egresados;

    public ReporteCarreraDTO(String carrera, int anio, long inscriptos, long egresados) {
        this.carrera = carrera;
        this.anio = anio;
        this.inscriptos = inscriptos;
        this.egresados = egresados;
    }


}
