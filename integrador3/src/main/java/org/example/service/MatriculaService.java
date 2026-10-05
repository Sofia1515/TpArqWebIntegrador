package org.example.service;
import jakarta.transaction.Transactional;
import org.example.models.Matricula;
import org.example.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatriculaService implements BaseService<Matricula> {
@Autowired
    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    @Override
    public List<Matricula> findAll() throws Exception {
        return List.of();
    }

    @Override
    public Matricula buscarPorDni(int id) throws Exception {
        return null;
    }

    @Override
    public Matricula guardar(Matricula entity) throws Exception {
        return null;
    }

    @Override
    public Matricula update(Long id, Matricula entity) throws Exception {
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        return false;
    }
}