package org.example.service;

import org.example.dto.ReporteCarreraDTO;
import org.example.models.Carrera;
import org.example.repository.CarreraRepository;
import org.springframework.stereotype.Service;
import org.example.dto.CarreraDTO;

import java.util.List;

@Service
public class CarreraService implements BaseService<Carrera> {

    private final CarreraRepository carreraRepository;

    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }


    public List<CarreraDTO> obtenerCarrerasConInscriptos() throws Exception {
        try {
            return carreraRepository.obtenerCarrerasConInscriptos();
        } catch (Exception e) {
            throw new Exception("Error al obtener carreras con inscriptos: " + e.getMessage());
        }
    }

    public List<ReporteCarreraDTO> obtenerReporteCarreras() throws Exception {
        try {
            return carreraRepository.obtenerReporteCarreras();
        } catch (Exception e) {
            throw new Exception("Error al generar el reporte de carreras: " + e.getMessage());
        }
    }

    @Override
    public List<Carrera> findAll() throws Exception {
        return carreraRepository.findAll();
    }

    @Override
    public Carrera buscarPorDni(int id) throws Exception {
        return carreraRepository.findById(id).orElse(null);
    }

    @Override
    public Carrera guardar(Carrera carrera) throws Exception {
        return carreraRepository.save(carrera);
    }

    @Override
    public Carrera update(Long id, Carrera entity) throws Exception {
        if (carreraRepository.existsById(id.intValue())) {
            return carreraRepository.save(entity);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (carreraRepository.existsById(id.intValue())) {
            carreraRepository.deleteById(id.intValue());
            return true;
        }
        return false;
    }
}