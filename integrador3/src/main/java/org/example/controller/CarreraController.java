package org.example.controller;
import org.example.dto.CarreraDTO;
import org.example.service.CarreraService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    private final CarreraService carreraService;

    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping
    public List<CarreraDTO> obtenerCarreras() {
        return carreraService.obtenerCarrerasConCantidadInscriptos();
    }
}