package org.example.controller;
import org.example.service.CarreraService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}

