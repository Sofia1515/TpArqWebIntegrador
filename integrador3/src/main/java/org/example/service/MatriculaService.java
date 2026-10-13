package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.MatriculaCreateDTO;
import org.example.models.Carrera;
import org.example.models.Estudiante;
import org.example.models.Matricula;
import org.example.repository.CarreraRepository;
import org.example.repository.EstudianteRepository;
import org.example.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatriculaService implements BaseService<Matricula> {

    private final MatriculaRepository matriculaRepository;
    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;

    public MatriculaService(MatriculaRepository matriculaRepository, EstudianteRepository estudianteRepository, CarreraRepository carreraRepository) {
        this.matriculaRepository = matriculaRepository;
        this.estudianteRepository = estudianteRepository;
        this.carreraRepository = carreraRepository;
    }

    //Para matricular un estudiante (Punto B)
    @Transactional
    public MatriculaCreateDTO matricular(MatriculaCreateDTO dto) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(dto.getDniEstudiante())
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));
        Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                .orElseThrow(() -> new Exception("Carrera no encontrada"));

        Matricula matricula = Matricula.builder()
                .id(dto.getId())
                .estudiante(estudiante)
                .carrera(carrera)
                .inscripcion(dto.getInscripcion())
                .graduacion(dto.getGraduacion())
                .antiguedad(dto.getAntiguedad())
                .build();

        matriculaRepository.save(matricula);
        return dto;
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