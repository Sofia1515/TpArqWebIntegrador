package org.example.repository;

import org.example.models.Matricula;
import org.springframework.stereotype.Repository;

@Repository("MatriculaRepositorio")
    public interface MatriculaRepository extends RepoBase<Matricula, Integer>{
}
