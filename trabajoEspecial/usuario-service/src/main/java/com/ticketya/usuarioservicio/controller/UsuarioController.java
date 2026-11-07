package com.ticketya.usuarioservicio.controller;


import com.ticketya.usuarioservicio.dto.UsuarioCreateDTO;
import com.ticketya.usuarioservicio.dto.UsuarioDTO;
import com.ticketya.usuarioservicio.models.Rol;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ticketya.usuarioservicio.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    // POST /api/usuarios -> registrar un usuario nuevo
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioCreateDTO dto){
        UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(dto);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // GET /api/usuarios/{id} -> obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) throws Exception {
        UsuarioDTO usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // GET /api/usuarios/{email} -> obtener un usuario por email
    @GetMapping("/{email}")
    public ResponseEntity<UsuarioDTO> obtenerPorEmail(@PathVariable String email) throws Exception {
        UsuarioDTO usuario = usuarioService.obtenerPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    // GET /api/usuarios -> obtener todos los usuarios
    // GET /api/usuarios?rol=ORGANIZADOR -> filtrar los usuarios de un rol especifico
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerPorRol(@RequestParam(required = false) Rol rol) throws Exception{
        if(rol != null){
            List<UsuarioDTO> usuarios = usuarioService.obtenerPorRol(rol);
            return ResponseEntity.ok(usuarios);
        }
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }
}
