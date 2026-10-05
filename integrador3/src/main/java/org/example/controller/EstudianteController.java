package org.example.controller;

import org.example.dto.EstudianteDTO;
import org.example.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<EstudianteDTO>> obtenerTodosOrdenados() {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.obtenerTodosOrdenados();
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}