package org.example.repository;

import org.example.dto.EstudianteDTO;
import org.example.models.Estudiante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository("EstudianteRepositorio")
public interface EstudianteRepository extends RepoBase<Estudiante, Integer> {

    @Query("SELECT new org.example.dto.EstudianteDTO(e.nombre, e.apellido, e.libretaUniversitaria,e.ciudadResidencia, e.genero) " +
            "FROM Estudiante e ORDER BY e.apellido ASC, e.nombre ASC")
    List<EstudianteDTO> obtenerTodosOrdenados();

    @Query("SELECT new org.example.dto.EstudianteDTO(e.nombre, e.apellido, e.libretaUniversitaria,e.ciudadResidencia, e.genero) " +
            "FROM Estudiante e WHERE e.libretaUniversitaria = :libreta")
    Optional<EstudianteDTO> buscarPorLibreta(@Param("libreta") int libreta);

}