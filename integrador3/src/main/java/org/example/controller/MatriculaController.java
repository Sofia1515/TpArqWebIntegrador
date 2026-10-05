package org.example.controller;

import org.example.models.Matricula;
import org.example.service.MatriculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    // b) Matricular un estudiante en una carrera
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Matricula matricula) {
        try {
            Matricula nuevaMatricula = matriculaService.guardar(matricula);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMatricula);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al matricular estudiante: " + e.getMessage() + "\"}");
        }
    }
}