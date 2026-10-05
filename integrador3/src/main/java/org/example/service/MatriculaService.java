package org.example.service;
import org.example.models.Matricula;
import org.example.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public Matricula guardar(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }
}