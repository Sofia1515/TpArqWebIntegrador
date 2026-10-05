package org.example.service;

import org.example.dto.EstudianteDTO;
import org.example.models.Estudiante;
import org.example.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante buscarPorDni(int dni) {
        return estudianteRepository.findById(dni).orElse(null);
    }

    public List<EstudianteDTO> obtenerTodosOrdenados() {
        return estudianteRepository.obtenerTodosOrdenados();
    }

    public EstudianteDTO buscarPorLibreta(int libreta) {
        return estudianteRepository.buscarPorLibreta(libreta);
    }

    public List<EstudianteDTO> buscarPorGenero(String genero) {
        return estudianteRepository.buscarPorGenero(genero);
    }

    public List<EstudianteDTO> buscarPorCarreraYCiudad(
            String carrera,
            String ciudad) {

        return estudianteRepository.buscarPorCarreraYCiudad(
                carrera,
                ciudad
        );
    }
}