package com.ticketya.reservaServicio.controller;
import com.ticketya.reservaServicio.dto.ValidarQrRequestDTO;
import com.ticketya.reservaServicio.dto.ValidarQrResponseDTO;
import com.ticketya.reservaServicio.service.EntradaService;
import com.ticketya.reservaServicio.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entradas")
@Tag(name = "Entradas y Control de Acceso", description = "Validación de códigos QR en el ingreso al evento")
public class EntradaController {
    private final EntradaService entradaService;
    private final ReservaService reservaService;

    public EntradaController(EntradaService entradaService, ReservaService reservaService) {
        this.entradaService = entradaService;
        this.reservaService = reservaService;
    }

    @Operation(summary = "Validar código QR en puerta", description = "Verifica que la entrada exista, pertenezca al evento y no haya sido usada, marcándola como UTILIZADA.")
    @PostMapping("/validar-qr")
    public ResponseEntity<ValidarQrResponseDTO> validarQr(@RequestBody ValidarQrRequestDTO request) {
        return ResponseEntity.ok(entradaService.validarIngreso(request));
    }

    @Operation(summary = "Solicitar devolucion de entrada", description = "Permite solicitar la devolucion con al menos 72 hs de anticipacion al evento.")
    @PostMapping("/devoluciones/{id}/solicitar")
    public ResponseEntity<String> solicitarDevolucion(@PathVariable Long id, @RequestHeader("X-User-Id") Long idUsuario) {
        reservaService.solicitarDevolucion(id, idUsuario);
        return ResponseEntity.ok("Solicitud de devolucion registrada.");
    }

    @Operation(summary = "Aprobar devolucion de entrada", description = "Aprueba la solicitud pendiente, marca la entrada como DEVUELTA y libera el asiento.")
    @PutMapping("/devoluciones/{id}/aprobar")
    public ResponseEntity<String> aprobarDevolucion(@PathVariable Long id) {
        reservaService.aprobarDevolucion(id);
        return ResponseEntity.ok("Devolucion aprobada y asiento liberado.");
    }

    @Operation(summary = "Rechazar devolucion de entrada", description = "Rechaza la solicitud pendiente y devuelve la entrada a estado NO_USADA.")
    @PutMapping("/devoluciones/{id}/rechazar")
    public ResponseEntity<String> rechazarDevolucion(@PathVariable Long id) {
        reservaService.rechazarDevolucion(id);
        return ResponseEntity.ok("Devolucion rechazada. La entrada sigue vigente.");
    }

    @Operation(summary = "Cancelar entradas por cancelacion de evento", description = "Marca como DEVUELTA todas las entradas activas de un evento cancelado.")
    @PutMapping("/evento/{idEvento}/cancelar-entradas")
    public ResponseEntity<String> cancelarEntradasPorEvento(@PathVariable Long idEvento) {
        reservaService.cancelarEntradasPorEvento(idEvento);
        return ResponseEntity.ok("Entradas del evento marcadas para reintegro.");
    }

}
