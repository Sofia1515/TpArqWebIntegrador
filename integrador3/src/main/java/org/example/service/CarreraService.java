package org.example.service;

import org.example.dto.CarreraDTO;
import org.example.models.Carrera;
import org.example.repository.CarreraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarreraService {

    private final CarreraRepository carreraRepository;

    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    public Carrera guardar(Carrera carrera) {
        return carreraRepository.save(carrera);
    }

    public Carrera buscarPorId(int id) {
        return carreraRepository.findById(id).orElse(null);
    }


}