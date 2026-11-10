package com.ticketya.usuarioservicio.controller;


import com.ticketya.usuarioservicio.dto.UsuarioCreateDTO;
import com.ticketya.usuarioservicio.dto.UsuarioDTO;
import com.ticketya.usuarioservicio.models.Rol;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ticketya.usuarioservicio.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Endpoints para la gestion, registro y consulta de usuarios (US-05 y US-06)")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    // POST /api/usuarios -> US-05: Registrar un usuario nuevo
    @Operation(
            summary = "Registrar un usuario nuevo",
            description = "Crea un usuario en el sistema con un rol específico (CLIENTE, ORGANIZADOR, ADMINISTRADOR, PERSONAL_ACCESO) - US-05."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o incompletos"),
            @ApiResponse(responseCode = "409", description = "El email ingresado ya se encuentra registrado")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioCreateDTO dto){
        UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(dto);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // GET /api/usuarios/{id} -> US-06: Obtener un usuario por su ID
    @Operation(
            summary = "Obtener un usuario por su ID",
            description = "Retorna la información del perfil del usuario según su ID único - US-06."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró ningún usuario con el ID especificado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) throws Exception {
        UsuarioDTO usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // GET /api/usuarios/email/{email} -> US-06: Obtener un usuario por email
    @GetMapping("/email/{email}")
    @Operation(
            summary = "Obtener un usuario por Email",
            description = "Busca y retorna un usuario asociado a la direccion de correo electronico provista - US-06."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontro ningun usuario asociado a dicho email")
    })
    public ResponseEntity<UsuarioDTO> obtenerPorEmail(@PathVariable String email) throws Exception {
        UsuarioDTO usuario = usuarioService.obtenerPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    // GET /api/usuarios -> obtener todos los usuarios
    // GET /api/usuarios?rol=ORGANIZADOR -> filtrar los usuarios de un rol especifico
    @Operation(
            summary = "Listar o filtrar usuarios",
            description = "Retorna la lista completa de usuarios. Permite filtrar opcionalmente por rol pasándolo como query parameter (?rol=ORGANIZADOR)."
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerPorRol(@RequestParam(required = false) Rol rol) throws Exception{
        if(rol != null){
            List<UsuarioDTO> usuarios = usuarioService.obtenerPorRol(rol);
            return ResponseEntity.ok(usuarios);
        }
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }
}
