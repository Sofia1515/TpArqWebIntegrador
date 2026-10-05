package org.example.repository;

import org.example.dto.ReporteCarreraDTO;
import org.example.entities.Matricula;
import java.util.List;

public interface MatriculaRepository {

    // Insertar (Guardar)
    void guardar(Matricula matricula);

    List<ReporteCarreraDTO> obtenerReporteCarreras();
}