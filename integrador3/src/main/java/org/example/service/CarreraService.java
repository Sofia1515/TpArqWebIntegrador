package org.example.service;

import org.example.models.Carrera;
import org.example.repository.CarreraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarreraService implements BaseService<Carrera>  {

    private final CarreraRepository carreraRepository;

    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Override
    public List<Carrera> findAll() throws Exception {
        return List.of();
    }

    @Override
    public Carrera buscarPorDni(int id) throws Exception {
        return null;
    }

    public Carrera guardar(Carrera carrera) {
        return carreraRepository.save(carrera);
    }

    @Override
    public Carrera update(Long id, Carrera entity) throws Exception {
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        return false;
    }

    public Carrera buscarPorId(int id) {
        return carreraRepository.findById(id).orElse(null);
    }


}