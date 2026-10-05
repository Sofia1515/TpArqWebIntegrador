package org.example.repository;

import org.example.models.Estudiante;
import org.springframework.stereotype.Repository;

@Repository("EstudianteRepositorio")
public interface EstudianteRepository extends RepoBase<Estudiante, Integer> {

}