package controller;

import models.Compra;
import models.Reserva;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<Reserva> crear(@RequestBody Reserva reserva) {
        Reserva nueva = reservaService.crearReserva(
                reserva.getIdUsuario(), reserva.getIdEvento(),
                reserva.getIdSector(), reserva.getFila(), reserva.getAsiento()
        );
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Compra> confirmar(@PathVariable Long id, @RequestParam String medioDePago) {
        return ResponseEntity.ok(reservaService.confirmarCompra(id, medioDePago));
    }

    @PutMapping("/{id}/liberar")
    public ResponseEntity<Void> liberar(@PathVariable Long id) {
        reservaService.liberarReserva(id);
        return ResponseEntity.noContent().build();
    }
}