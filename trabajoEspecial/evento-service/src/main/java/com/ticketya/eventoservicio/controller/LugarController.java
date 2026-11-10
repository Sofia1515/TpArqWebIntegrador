package com.ticketya.eventoservicio.controller;

import com.ticketya.eventoservicio.dto.LugarDTO;
import com.ticketya.eventoservicio.service.LugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/lugares")
@Tag(name = "Lugares", description = "Endpoints para la gestión de recintos, capacidad y geolocalización (US-01)")
public class LugarController {

    private final LugarService lugarService;

    @Autowired
    public LugarController(LugarService lugarService) {
        this.lugarService = lugarService;
    }

    // POST /api/lugares -> Crear lugar (US-01)
    @Operation(
            summary = "Crear nuevo recinto/lugar",
            description = "Registra un nuevo recinto en el sistema con su capacidad total y coordenadas de geolocalización - US-01."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lugar creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos o incompletos")
    })
    @PostMapping
    public ResponseEntity<LugarDTO> crearLugar(@RequestBody LugarDTO dto){
        LugarDTO lugar = lugarService.crearLugar(dto);
        return new ResponseEntity<>(lugar,HttpStatus.CREATED);
    }

    // GET /api/lugares/{id}
    @Operation(
            summary = "Obtener lugar por ID",
            description = "Retorna la informacion detallada de un recinto a partir de su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lugar encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontro ningún lugar con el ID especificado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LugarDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(lugarService.obtenerPorId(id));
    }

    // GET /api/lugares
    @Operation(
            summary = "Obtener todos los lugares",
            description = "Retorna la lista completa de recintos registrados en la plataforma."
    )
    @ApiResponse(responseCode = "200", description = "Lista de lugares obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<LugarDTO>> obtenerTodos(){
        return ResponseEntity.ok(lugarService.obtenerTodos());
    }

    // GET /api/lugares/buscar?ciudad={ciudad}
    @Operation(
            summary = "Buscar recintos por ciudad",
            description = "Filtra y retorna los lugares/recintos ubicados en una ciudad especifica."
    )
    @ApiResponse(responseCode = "200", description = "Lista de lugares filtrados obtenida exitosamente")
    @GetMapping("/buscar")
    public ResponseEntity<List<LugarDTO>> obtenerPorCiudad(@RequestParam String ciudad){
        return ResponseEntity.ok(lugarService.obtenerPorCiudad(ciudad));
    }
}
