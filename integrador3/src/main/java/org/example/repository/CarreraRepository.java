package org.example.repository;

import org.example.dto.CarreraDTO;
import org.example.dto.ReporteCarreraDTO;
import org.example.models.Carrera;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CarreraRepositorio")
public interface CarreraRepository extends RepoBase<Carrera, Integer> {

    @Query("SELECT new org.example.dto.CarreraDTO(c.nombre, COUNT(m.id)) " +
           "FROM Matricula m JOIN m.carrera c " +
           "GROUP BY c.id, c.nombre " +
           "ORDER BY COUNT(m.id) DESC")
    List<CarreraDTO> obtenerCarrerasConInscriptos();

    @Query("SELECT new org.example.dto.ReporteCarreraDTO(" +
            "c.nombre, m.inscripcion, COUNT(m.id), " +
            "SUM(CASE WHEN m.graduacion > 0 THEN 1 ELSE 0 END)) " +
            "FROM Matricula m JOIN m.carrera c " +
            "GROUP BY c.nombre, m.inscripcion " +
            "ORDER BY c.nombre ASC, m.inscripcion ASC")
    List<ReporteCarreraDTO> obtenerReporteCarreras();
}