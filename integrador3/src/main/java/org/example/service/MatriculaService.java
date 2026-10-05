package org.example.service;

import org.example.models.Matricula;
import org.example.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatriculaService implements BaseService<Matricula> {

    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    @Override
    public List<Matricula> findAll() throws Exception {
        return matriculaRepository.findAll();
    }

    @Override
    public Matricula buscarPorDni(int id) throws Exception {
        return matriculaRepository.findById(id).orElse(null);
    }

    @Override
    public Matricula guardar(Matricula entity) throws Exception {
        return matriculaRepository.save(entity);
    }

    @Override
    public Matricula update(Long id, Matricula entity) throws Exception {
        if (matriculaRepository.existsById(id.intValue())) {
            return matriculaRepository.save(entity);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (matriculaRepository.existsById(id.intValue())) {
            matriculaRepository.deleteById(id.intValue());
            return true;
        }
        return false;
    }
}