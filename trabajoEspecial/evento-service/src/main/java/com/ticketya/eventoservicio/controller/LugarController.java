package com.ticketya.eventoservicio.controller;

import com.ticketya.eventoservicio.dto.LugarDTO;
import com.ticketya.eventoservicio.service.LugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugares")
public class LugarController {

    private final LugarService lugarService;

    @Autowired
    public LugarController(LugarService lugarService) {
        this.lugarService = lugarService;
    }

    // POST /api/lugares -> Crear lugar (US-01)
    @PostMapping
    public ResponseEntity<LugarDTO> crearLugar(@RequestBody LugarDTO dto){
        LugarDTO lugar = lugarService.crearLugar(dto);
        return new ResponseEntity<>(lugar,HttpStatus.CREATED);
    }

    // GET /api/lugares/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LugarDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(lugarService.obtenerPorId(id));
    }

    // GET /api/lugares
    @GetMapping
    public ResponseEntity<List<LugarDTO>> obtenerTodos(){
        return ResponseEntity.ok(lugarService.obtenerTodos());
    }

    // GET /api/lugares/buscar?ciudad={ciudad}
    @GetMapping("/buscar")
    public ResponseEntity<List<LugarDTO>> obtenerPorCiudad(@RequestParam String ciudad){
        return ResponseEntity.ok(lugarService.obtenerPorCiudad(ciudad));
    }
}
