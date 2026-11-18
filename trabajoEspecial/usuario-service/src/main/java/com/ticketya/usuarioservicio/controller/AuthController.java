package com.ticketya.usuarioservicio.controller;

import com.ticketya.usuarioservicio.dto.LoginRequestDTO;
import com.ticketya.usuarioservicio.dto.LoginResponseDTO;
import com.ticketya.usuarioservicio.models.Usuario;
import com.ticketya.usuarioservicio.repository.UsuarioRepository;
import com.ticketya.usuarioservicio.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthController(UsuarioRepository repo, PasswordEncoder encoder, JwtService jwtService) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO req) {
        Usuario u = repo.findByEmail(req.email()).orElse(null);
        if (u == null || !encoder.matches(req.password(), u.getPassword())) {
            // misma respuesta para "no existe" y "clave incorrecta"
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(
                new LoginResponseDTO(jwtService.generarToken(u), "Bearer", u.getRol().name()));
    }
}
