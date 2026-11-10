package controller;

import models.Compra;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.CompraRepository;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/compras")
@Tag(name = "Compras", description = "Endpoints de administracion para la auditoría y consulta de transacciones efectuadas")
public class CompraController {

    private final CompraRepository compraRepository;

    public CompraController(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    // GET /api/compras -> Listar todas las compras (Admin)
    @Operation(
            summary = "Listar todas las compras",
            description = "Retorna el historial completo de compras confirmadas y transacciones registradas en el sistema."
    )
    @ApiResponse(responseCode = "200", description = "Historial de compras obtenido exitosamente")
    @GetMapping
    public ResponseEntity<List<Compra>> listarTodas() {
        return ResponseEntity.ok(compraRepository.findAll());
    }
}