
package com.ticketya.usuarioservicio.controller;

import com.ticketya.usuarioservicio.dto.EventoDTO;
import com.ticketya.usuarioservicio.models.Preferencia;
import com.ticketya.usuarioservicio.service.PreferenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preferencias")
public class PreferenciaController {

    private final PreferenciaService preferenciaService;

    public PreferenciaController(PreferenciaService preferenciaService) {
        this.preferenciaService = preferenciaService;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Preferencia> obtenerPreferencias(@PathVariable Long usuarioId) {

        return preferenciaService.obtenerPreferencias(usuarioId)
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

    @GetMapping("/{usuarioId}/recomendaciones")
    public ResponseEntity<List<EventoDTO>> obtenerRecomendaciones(@PathVariable Long usuarioId){
        List<EventoDTO> recomendaciones = preferenciaService.obtenerRecomendaciones(usuarioId);

        return ResponseEntity.ok(recomendaciones);
    }
}
