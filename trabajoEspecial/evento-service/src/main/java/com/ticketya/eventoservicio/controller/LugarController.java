package com.ticketya.eventoservicio.controller;

import com.ticketya.eventoservicio.dto.LugarDTO;
import com.ticketya.eventoservicio.models.Lugar;
import com.ticketya.eventoservicio.service.LugarService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController("/api/lugares")
public class LugarController {

    private final LugarService lugarService;

    @Autowired
    public LugarController(LugarService lugarService) {
        this.lugarService = lugarService;
    }

    @PostMapping
    public ResponseEntity<LugarDTO> crearLugar(@RequestBody LugarDTO dto){
        LugarDTO lugar = lugarService.crearLugar(dto);

        return new ResponseEntity<>(lugar,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LugarDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(lugarService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<LugarDTO>> obtenerTodos(){
        return ResponseEntity.ok(lugarService.obtenerTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LugarDTO>> obtenerPorCiudad(@RequestParam String ciudad){
        return ResponseEntity.ok(lugarService.obtenerPorCiudad(ciudad));
    }
}
