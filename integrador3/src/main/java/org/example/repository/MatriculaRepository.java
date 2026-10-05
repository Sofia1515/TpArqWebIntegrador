package org.example.repository;

import org.example.models.Estudiante;
import org.springframework.stereotype.Repository;

@Repository("MatriculaRepositorio")
    public interface MatriculaRepository extends RepoBase<Estudiante, Integer>{
}
