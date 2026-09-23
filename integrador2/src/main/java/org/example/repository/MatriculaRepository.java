package org.example.repository;

import org.example.entities.Matricula;
import java.util.List;

public interface MatriculaRepository {

    // Insertar (Guardar)
    void guardar(Matricula matricula);

    // Reporte de inscriptos por carrera y año 
    List obtenerInscriptosPorCarreraYAnio(); 
    
    // Reporte de egresados por carrera y año 
    List obtenerEgresadosPorCarreraYAnio();
}