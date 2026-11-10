package controller;

import models.Compra;
import models.Reserva;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "Endpoints para la reserva temporal de asientos, confirmacion de compras y liberación de cupos (US-08 a US-10)")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // POST /api/reservas -> Crear reserva temporal (US-08)
    @Operation(
            summary = "Crear reserva temporal",
            description = "Reserva un asiento específico por un lapso de 10 minutos para iniciar el proceso de compra - US-08."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "El asiento ya se encuentra reservado o la solicitud es invalida - US-09")
    })
    @PostMapping
    public ResponseEntity<Reserva> crear(@RequestBody Reserva reserva) {
        Reserva nueva = reservaService.crearReserva(
                reserva.getIdUsuario(), reserva.getIdEvento(),
                reserva.getIdSector(), reserva.getFila(), reserva.getAsiento()
        );
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // PUT /api/reservas/{id}/confirmar -> Confirmar compra (US-10)
    @Operation(
            summary = "Confirmar compra",
            description = "Procesa el pago mock de una reserva vigente, confirmando la venta y generando la entrada con QR - US-10."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compra confirmada y entradas emitidas exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la reserva o se encuentra expirada")
    })
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Compra> confirmar(@PathVariable Long id, @RequestParam String medioDePago) {
        return ResponseEntity.ok(reservaService.confirmarCompra(id, medioDePago));
    }

    // PUT /api/reservas/{id}/liberar -> Liberar reserva vencida/cancelada
    @Operation(
            summary = "Liberar reserva",
            description = "Cancela o expira manualmente una reserva temporal, liberando la ubicacion para otros usuarios."
    )
    @ApiResponse(responseCode = "204", description = "Reserva liberada con exito")
    @PutMapping("/{id}/liberar")
    public ResponseEntity<Void> liberar(@PathVariable Long id) {
        reservaService.liberarReserva(id);
        return ResponseEntity.noContent().build();
    }
}