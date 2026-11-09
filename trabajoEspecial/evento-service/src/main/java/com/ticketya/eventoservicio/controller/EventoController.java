package com.ticketya.eventoservicio.controller;

import com.ticketya.eventoservicio.dto.CrearEventoDTO;
import com.ticketya.eventoservicio.dto.EventoDTO;
import com.ticketya.eventoservicio.models.EstadoEvento;
import com.ticketya.eventoservicio.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // POST /api/eventos -> Crear evento en BORRADOR (US-02)
    @PostMapping
    public ResponseEntity<EventoDTO> crearEvento(@RequestBody CrearEventoDTO dto){
        EventoDTO nuevoEvento = eventoService.crearEvento(dto);
        return new ResponseEntity<>(nuevoEvento, HttpStatus.CREATED);
    }

    // GET /api/eventos/{id} -> Detalle de un evento (US-04)
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> obtenerPorId(@PathVariable Long id){
        EventoDTO evento = eventoService.obtenerPorId(id);

        return new ResponseEntity<>(evento, HttpStatus.OK);
    }

    // GET /api/eventos -> obtener todos los eventos
    @GetMapping
    public ResponseEntity<List<EventoDTO>> obtenerTodos(){
        return ResponseEntity.ok(eventoService.obtenerTodos());
    }

    // GET /api/eventos/organizador/{idOrganizador} -> devuelve todos los eventos de un organizador
    @GetMapping("/organizador/{idOrganizador}")
    public ResponseEntity<List<EventoDTO>> obtenerPorIdOrganizador(@PathVariable Long idOrganizador){
        return ResponseEntity.ok(eventoService.obtenerPorIdOrganizador(idOrganizador));
    }

    // GET /api/eventos/buscar?ciudad={ciudad}&fechaInicio={fechaInicio}&fechaFin={fechaFin}
    @GetMapping("/buscar")
    public ResponseEntity<List<EventoDTO>> obtenerPorCiudadYRangoDeFechas(@RequestParam(required = false)String ciudad, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin){
        List<EventoDTO> resultados = eventoService.obtenerPorCiudadYRangoDeFechas(ciudad,fechaInicio,fechaFin);

        return ResponseEntity.ok(resultados);
    }

    // PUT /api/eventos/{id}/estado/?nuevoEstado={nuevoEstado} -> cambia el estado de un evento
    @PutMapping("/{id}/estado")
    public ResponseEntity<EventoDTO> cambiarEstado(@PathVariable Long id, @RequestParam EstadoEvento nuevoEstado){
        EventoDTO actualizado = eventoService.cambiarEstado(id, nuevoEstado);

        return ResponseEntity.ok(actualizado);
    }

    // DELETE /api/eventos/{id} -> elimina un evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id){
        eventoService.eliminarEvento(id);

        return ResponseEntity.noContent().build();
    }
}
