package org.example.controller;

import org.example.dto.ReporteCarreraDTO;
import org.example.service.CarreraService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.example.dto.CarreraDTO;

import java.util.List;


@RestController
@RequestMapping("/carreras")
public class CarreraController {

    private final CarreraService carreraService;

    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping("/inscriptos")
    public ResponseEntity<?> obtenerCarrerasConInscriptos() {
        try {
            List<CarreraDTO> carreras =
                    carreraService.obtenerCarrerasConInscriptos();

            return ResponseEntity.status(HttpStatus.OK).body(carreras);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/reporte")
    public ResponseEntity<?> obtenerReporteCarreras() {
        try {
            List<ReporteCarreraDTO> reporte = carreraService.obtenerReporteCarreras();
            return ResponseEntity.status(HttpStatus.OK).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}