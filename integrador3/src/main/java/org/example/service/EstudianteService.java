package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.EstudianteDTO;
import org.example.models.Estudiante;
import org.example.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService implements BaseService<Estudiante> {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    // Método custom para la consulta del TP
    @Transactional
    public List<EstudianteDTO> obtenerTodosOrdenados() throws Exception {
        try {
            return estudianteRepository.obtenerTodosOrdenados();
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes ordenados: " + e.getMessage());
        }
    }

    @Override
    public List<Estudiante> findAll() throws Exception {
        return estudianteRepository.findAll();
    }

    @Override
    public Estudiante buscarPorDni(int dni) throws Exception {
        return estudianteRepository.findById(dni).orElse(null);
    }

    @Override
    public Estudiante guardar(Estudiante estudiante) throws Exception {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Estudiante update(Long id, Estudiante entity) throws Exception {
        if (estudianteRepository.existsById(id.intValue())) {
            return estudianteRepository.save(entity);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (estudianteRepository.existsById(id.intValue())) {
            estudianteRepository.deleteById(id.intValue());
            return true;
        }
        return false;
    }
}