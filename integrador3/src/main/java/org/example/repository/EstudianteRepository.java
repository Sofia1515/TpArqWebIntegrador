package org.example.repository;

import org.example.dto.EstudianteDTO;
import org.example.models.Estudiante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("EstudianteRepositorio")
public interface EstudianteRepository extends RepoBase<Estudiante, Integer> {

    @Query("SELECT new org.example.dto.EstudianteDTO(e.nombre, e.apellido, e.libretaUniversitaria,e.ciudadResidencia, e.genero) " +
            "FROM Estudiante e ORDER BY e.apellido ASC, e.nombre ASC")
    List<EstudianteDTO> obtenerTodosOrdenados();

}