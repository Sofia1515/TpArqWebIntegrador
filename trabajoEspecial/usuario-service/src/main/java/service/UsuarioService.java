package service;

import dto.UsuarioCreateDTO;
import dto.UsuarioDTO;
import models.Rol;
import models.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioDTO crearUsuario(UsuarioCreateDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email " + dto.getEmail() + " ya esta en uso.");
        }

        // convertir el DTO en una entidad Usuario para guardar en la base
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());

        // guardar el usuario en la base
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return convertirADTO(usuarioGuardado);
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
