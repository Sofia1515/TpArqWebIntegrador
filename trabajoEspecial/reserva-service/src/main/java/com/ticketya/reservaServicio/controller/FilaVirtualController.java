
package com.ticketya.reservaServicio.controller;

import com.ticketya.reservaServicio.cliente.EventoClient;
import com.ticketya.reservaServicio.dto.EventoDTO;
import com.ticketya.reservaServicio.service.FilaVirtualService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fila")
public class FilaVirtualController {

    private final FilaVirtualService filaVirtualService;
    private final EventoClient eventoClient;

    public FilaVirtualController(
            FilaVirtualService filaVirtualService,
            EventoClient eventoClient) {
        this.filaVirtualService = filaVirtualService;
        this.eventoClient = eventoClient;
    }

    // El usuario ingresa a la fila de un evento.
    @PostMapping("/eventos/{idEvento}/ingresar")
    public ResponseEntity<Map<String, Object>> ingresar(
            @PathVariable Long idEvento,
            @RequestParam Long idUsuario) {

        EventoDTO evento = eventoClient.obtenerEventoPorId(idEvento);

        if (evento == null || !"PUBLICADO".equalsIgnoreCase(evento.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El evento no existe o no está publicado."
            );
        }

        if (!evento.isAltaDemanda()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Este evento no requiere fila virtual."
            );
        }

        Long posicion = filaVirtualService.ingresar(idEvento, idUsuario);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Ingreso a la fila virtual exitoso.");
        respuesta.put("idEvento", idEvento);
        respuesta.put("idUsuario", idUsuario);
        respuesta.put("posicion", filaVirtualService.consultarPosicion(idEvento, idUsuario));

        return ResponseEntity.ok(respuesta);
    }

    // Consulta la posición actual del usuario.
    @GetMapping("/eventos/{idEvento}/posicion")
    public ResponseEntity<Map<String, Object>> consultarPosicion(
            @PathVariable Long idEvento,
            @RequestParam Long idUsuario) {

        Long posicion = filaVirtualService.consultarPosicion(idEvento, idUsuario);

        if (posicion == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario no se encuentra en la fila."
            );
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("idEvento", idEvento);
        respuesta.put("idUsuario", idUsuario);
        respuesta.put("posicion", posicion);

        return ResponseEntity.ok(respuesta);
    }

    // Habilita una tanda de usuarios para comprar.
    @PostMapping("/eventos/{idEvento}/habilitar")
    public ResponseEntity<Map<String, Object>> habilitarTanda(
            @PathVariable Long idEvento,
            @RequestParam int cantidad) {

        List<String> habilitados =
                filaVirtualService.habilitarTanda(idEvento, cantidad);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Tanda habilitada.");
        respuesta.put("cantidadHabilitada", habilitados.size());
        respuesta.put("usuariosHabilitados", habilitados);

        return ResponseEntity.ok(respuesta);
    }
}

