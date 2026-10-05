package org.example.repository;

import org.example.dto.CarreraDTO;
import org.example.models.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarreraRepository extends JpaRepository<Carrera, Integer> {

    @Query("""
        SELECT new org.example.dto.CarreraDTO(
            c.nombre,
            COUNT(m.estudiante)
        )
        FROM Matricula m
        JOIN m.carrera c
        GROUP BY c.nombre
        ORDER BY COUNT(m.estudiante) DESC
    """)
    List<CarreraDTO> obtenerCarrerasConCantidadInscriptos();
}