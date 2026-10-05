package org.example.repository;

import org.example.models.Carrera;
import org.springframework.stereotype.Repository;

@Repository("CarreraRepositorio")
public interface CarreraRepository extends RepoBase<Carrera, Integer> {

}