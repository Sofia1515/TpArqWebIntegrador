package org.example.controller;

import org.example.dto.EstudianteCreateDTO;
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
    public ResponseEntity<EstudianteDTO> guardar(@RequestBody EstudianteCreateDTO dto) {
        try {
            EstudianteDTO estudianteGuardado = estudianteService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(estudianteGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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

    // d) Obtener un estudiante por libreta universitaria
    @GetMapping("/libreta/{libreta}")
    public ResponseEntity<EstudianteDTO> buscarPorLibreta(@PathVariable int libreta) {
        try {
            EstudianteDTO estudiante = estudianteService.buscarPorLibreta(libreta);
            return ResponseEntity.ok(estudiante);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // e) Recuperar estudiantes por género
    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> buscarPorGenero(@PathVariable String genero) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(estudianteService.buscarPorGenero(genero));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    @GetMapping("/carrera/{carrera}")
    public ResponseEntity<List<EstudianteDTO>> porCarreraYCiudad(
            @PathVariable String carrera,
            @RequestParam String ciudad) {
        return ResponseEntity.ok(estudianteService.buscarPorCarreraYCiudad(carrera, ciudad));
    }
}