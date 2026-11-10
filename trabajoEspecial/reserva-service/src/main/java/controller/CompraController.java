package controller;

import models.Compra;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.CompraRepository;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraRepository compraRepository;

    public CompraController(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    // GET /api/compras -> Listar todas las compras (Admin)
    @GetMapping
    public ResponseEntity<List<Compra>> listarTodas() {
        return ResponseEntity.ok(compraRepository.findAll());
    }
}