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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@Tag(name = "Eventos", description = "Endpoints para la gestión, publicación y búsqueda de eventos (US-02, US-03 y US-04)")
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // POST /api/eventos -> Crear evento en BORRADOR (US-02)
    @Operation(
            summary = "Crear evento en borrador",
            description = "Crea un evento en estado BORRADOR asociando recintos y sectores (US-02)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento creado exitosamente en estado BORRADOR"),
            @ApiResponse(responseCode = "400", description = "Lugar inexistente o datos de entrada invalidos")
    })
    @PostMapping
    public ResponseEntity<EventoDTO> crearEvento(@RequestBody CrearEventoDTO dto){
        EventoDTO nuevoEvento = eventoService.crearEvento(dto);
        return new ResponseEntity<>(nuevoEvento, HttpStatus.CREATED);
    }

    // GET /api/eventos/{id} -> Detalle de un evento (US-04)
    @Operation(
            summary = "Obtener evento por ID",
            description = "Retorna el detalle completo de un evento por su identificador unico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontro ningun evento con el ID especificado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> obtenerPorId(@PathVariable Long id){
        EventoDTO evento = eventoService.obtenerPorId(id);

        return new ResponseEntity<>(evento, HttpStatus.OK);
    }

    // GET /api/eventos -> obtener todos los eventos
    @Operation(
            summary = "Obtener todos los eventos",
            description = "Retorna el listado completo de eventos registrados en el sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista de eventos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<EventoDTO>> obtenerTodos(){
        return ResponseEntity.ok(eventoService.obtenerTodos());
    }

    // GET /api/eventos/organizador/{idOrganizador} -> devuelve todos los eventos de un organizador
    @Operation(
            summary = "Obtener eventos de un organizador",
            description = "Retorna todos los eventos asociados al ID de un organizador especifico."
    )
    @ApiResponse(responseCode = "200", description = "Lista de eventos del organizador obtenida exitosamente")
    @GetMapping("/organizador/{idOrganizador}")
    public ResponseEntity<List<EventoDTO>> obtenerPorIdOrganizador(@PathVariable Long idOrganizador){
        return ResponseEntity.ok(eventoService.obtenerPorIdOrganizador(idOrganizador));
    }

    // GET /api/eventos/buscar?ciudad={ciudad}&fechaInicio={fechaInicio}&fechaFin={fechaFin}
    @Operation(
            summary = "Buscar eventos por ciudad y rango de fechas",
            description = "Busca eventos en estado PUBLICADO filtrados opcionalmente por ciudad y rango de fechas (US-04)."
    )
    @ApiResponse(responseCode = "200", description = "Resultados de busqueda obtenidos exitosamente")
    @GetMapping("/buscar")
    public ResponseEntity<List<EventoDTO>> obtenerPorCiudadYRangoDeFechas(@RequestParam(required = false)String ciudad, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin){
        List<EventoDTO> resultados = eventoService.obtenerPorCiudadYRangoDeFechas(ciudad,fechaInicio,fechaFin);

        return ResponseEntity.ok(resultados);
    }

    // PUT /api/eventos/{id}/estado/?nuevoEstado={nuevoEstado} -> cambia el estado de un evento
    @Operation(
            summary = "Cambiar estado de un evento",
            description = "Transiciona el estado de un evento (ej: BORRADOR a PUBLICADO) - US-03."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado cambiado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado para actualizar su estado")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<EventoDTO> cambiarEstado(@PathVariable Long id, @RequestParam EstadoEvento nuevoEstado){
        EventoDTO actualizado = eventoService.cambiarEstado(id, nuevoEstado);

        return ResponseEntity.ok(actualizado);
    }

    // DELETE /api/eventos/{id} -> elimina un evento
    @Operation(
            summary = "Eliminar un evento",
            description = "Elimina físicamente un evento del sistema por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Evento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe un evento con el ID especificado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id){
        eventoService.eliminarEvento(id);

        return ResponseEntity.noContent().build();
    }
}
