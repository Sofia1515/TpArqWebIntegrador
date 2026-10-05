package org.example.service;

import org.example.models.Carrera;
import org.example.repository.CarreraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarreraService implements BaseService<Carrera> {

    private final CarreraRepository carreraRepository;

    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
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