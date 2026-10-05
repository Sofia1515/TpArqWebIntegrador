package org.example.repository;

import org.example.dto.EstudianteDTO;
import org.example.models.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query("""
        SELECT new org.example.dto.EstudianteDTO(
            e.nombre,
            e.apellido,
            e.libretaUniversitaria,
            e.ciudadResidencia,
            e.genero
        )
        FROM Estudiante e
        ORDER BY e.apellido ASC, e.nombre ASC
    """)
    List<EstudianteDTO> obtenerTodosOrdenados();


    @Query("""
        SELECT new org.example.dto.EstudianteDTO(
            e.nombre,
            e.apellido,
            e.libretaUniversitaria,
            e.ciudadResidencia,
            e.genero
        )
        FROM Estudiante e
        WHERE e.libretaUniversitaria = :libreta
    """)
    EstudianteDTO buscarPorLibreta(
            @Param("libreta") int libretaUniversitaria
    );


    @Query("""
        SELECT new org.example.dto.EstudianteDTO(
            e.nombre,
            e.apellido,
            e.libretaUniversitaria,
            e.ciudadResidencia,
            e.genero
        )
        FROM Estudiante e
        WHERE e.genero = :genero
        ORDER BY e.apellido ASC, e.nombre ASC
    """)
    List<EstudianteDTO> buscarPorGenero(
            @Param("genero") String genero
    );


    @Query("""
        SELECT new org.example.dto.EstudianteDTO(
            e.nombre,
            e.apellido,
            e.libretaUniversitaria,
            e.ciudadResidencia,
            e.genero
        )
        FROM Estudiante e
        JOIN Matricula m ON m.estudiante = e
        JOIN m.carrera c
        WHERE c.nombre = :nombreCarrera
        AND e.ciudadResidencia = :ciudad
        ORDER BY e.apellido ASC, e.nombre ASC
    """)
    List<EstudianteDTO> buscarPorCarreraYCiudad(
            @Param("nombreCarrera") String nombreCarrera,
            @Param("ciudad") String ciudadResidencia
    );
}