package org.example.repository;

import org.example.models.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository
        extends JpaRepository<Matricula, Integer> {

}