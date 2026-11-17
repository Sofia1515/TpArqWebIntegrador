package com.ticketya.reservaServicio.controller;
import com.ticketya.reservaServicio.dto.ValidarQrRequestDTO;
import com.ticketya.reservaServicio.dto.ValidarQrResponseDTO;
import com.ticketya.reservaServicio.service.EntradaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entradas")
@Tag(name = "Entradas y Control de Acceso", description = "Validación de códigos QR en el ingreso al evento")
public class EntradaController {
    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @Operation(summary = "Validar código QR en puerta", description = "Verifica que la entrada exista, pertenezca al evento y no haya sido usada, marcándola como UTILIZADA.")
    @PostMapping("/validar-qr")
    public ResponseEntity<ValidarQrResponseDTO> validarQr(@RequestBody ValidarQrRequestDTO request) {
        return ResponseEntity.ok(entradaService.validarIngreso(request));
    }
}
