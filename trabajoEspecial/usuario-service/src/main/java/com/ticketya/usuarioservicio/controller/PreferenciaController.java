
package com.ticketya.usuarioservicio.controller;

import com.ticketya.usuarioservicio.models.Preferencia;
import com.ticketya.usuarioservicio.service.PreferenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferencias")
public class PreferenciaController {

    private final PreferenciaService preferenciaService;

    public PreferenciaController(PreferenciaService preferenciaService) {
        this.preferenciaService = preferenciaService;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Preferencia> obtenerPreferencias(@PathVariable Long usuarioId) {

        return preferenciaService.buscarPorUsuario(usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Preferencia> guardarPreferencias(
            @PathVariable Long usuarioId,
            @RequestBody Preferencia preferencia) {

        preferencia.setUsuarioId(usuarioId);

        Preferencia guardada = preferenciaService.guardar(preferencia);

        return ResponseEntity.ok(guardada);
    }
}
