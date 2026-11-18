package com.ticketya.usuarioservicio.service;

import com.ticketya.usuarioservicio.dto.UsuarioCreateDTO;
import com.ticketya.usuarioservicio.dto.UsuarioDTO;
import com.ticketya.usuarioservicio.models.Rol;
import com.ticketya.usuarioservicio.models.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ticketya.usuarioservicio.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Alta con el rol que venga en el DTO. Solo debe usarla un administrador.
    @Transactional
    public UsuarioDTO crearUsuario(UsuarioCreateDTO dto) {
        return crear(dto, dto.getRol());
    }

    // Registro público: siempre crea un CLIENTE, sin importar el rol que mande el cliente.
    @Transactional
    public UsuarioDTO registrarCliente(UsuarioCreateDTO dto) {
        return crear(dto, Rol.CLIENTE);
    }

    private UsuarioDTO crear(UsuarioCreateDTO dto, Rol rol) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email " + dto.getEmail() + " ya esta en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(rol);

        return convertirADTO(usuarioRepository.save(usuario));
    }


    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("No se encontró ningún usuario con ese id: " + id));

        return convertirADTO(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorEmail(String email) throws Exception{
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("No se encontro ningún usuario con ese email: " + email));

        return convertirADTO(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodos(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> dtos = new ArrayList<>();

        for(Usuario u: usuarios){
            dtos.add(convertirADTO(u));
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerPorRol(Rol rol){
        List<Usuario> usuarios = usuarioRepository.findByRol(rol);
        List<UsuarioDTO> dtos = new ArrayList<>();

        for(Usuario u: usuarios){
            dtos.add(convertirADTO(u));
        }

        return dtos;
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());

        return dto;
    }
}
