package org.example.controller;

import org.example.dto.EstudianteDTO;
import org.example.models.Estudiante;
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

    // a) Dar de alta un estudiante
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Estudiante estudiante) {
        try {
            Estudiante estudianteGuardado = estudianteService.guardar(estudiante);
            return ResponseEntity.status(HttpStatus.CREATED).body(estudianteGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al dar de alta el estudiante: " + e.getMessage() + "\"}");
        }
    }

    // c) Recuperar todos los estudiantes ordenados
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