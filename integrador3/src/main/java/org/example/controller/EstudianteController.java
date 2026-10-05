package org.example.controller;

import org.example.dto.EstudianteDTO;
import org.example.service.EstudianteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<EstudianteDTO> obtenerTodos() {
        return estudianteService.obtenerTodosOrdenados();
    }

    @GetMapping("/libreta/{libreta}")
    public EstudianteDTO buscarPorLibreta(@PathVariable int libreta) {
        return estudianteService.buscarPorLibreta(libreta);
    }

    @GetMapping("/genero/{genero}")
    public List<EstudianteDTO> buscarPorGenero(
            @PathVariable String genero) {

        return estudianteService.buscarPorGenero(genero);
    }

    @GetMapping("/carrera/{carrera}/ciudad/{ciudad}")
    public List<EstudianteDTO> buscarPorCarreraYCiudad(
            @PathVariable String carrera,
            @PathVariable String ciudad) {

        return estudianteService.buscarPorCarreraYCiudad(
                carrera,
                ciudad
        );
    }
}