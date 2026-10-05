package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.EstudianteDTO;
import org.example.models.Estudiante;
import org.example.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService implements BaseService<Estudiante>{
    @Autowired
    private final EstudianteRepository estudianteRepository;

    //constructor?
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    // Metodos propios del TP
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
        return List.of();
    }

    @Override
    public Estudiante buscarPorDni(int dni) {
        return estudianteRepository.findById(dni).orElse(null);
    }

    @Override
    public Estudiante update(Long id, Estudiante entity) throws Exception {
        return null;
    }

    @Override
    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public boolean delete(Long id) throws Exception {
        return false;
    }
}