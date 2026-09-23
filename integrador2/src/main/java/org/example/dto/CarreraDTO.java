package org.example.dto;

public class CarreraDTO {
    private String nombreCarrera;
    private long cantidadInscriptos;

    public CarreraDTO(String nombreCarrera, long cantidadInscriptos){
        this.nombreCarrera = nombreCarrera;
        this.cantidadInscriptos = cantidadInscriptos;
    }

    public String getNombreCarrera(){
        return nombreCarrera;
    }

    public long getCantidadInscriptos(){
        return cantidadInscriptos;
    }
}
