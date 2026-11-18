package com.ticketya.usuarioservicio.config;

import com.ticketya.usuarioservicio.models.Rol;
import com.ticketya.usuarioservicio.models.Usuario;
import com.ticketya.usuarioservicio.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements CommandLineRunner {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public DatosIniciales(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        // El orden mantiene los IDs 1, 2, 3 del README
        crearSiNoExiste("Juan", "Perez", "juan.perez@example.com", Rol.CLIENTE);
        crearSiNoExiste("Organizador", "Demo", "organizador@example.com", Rol.ORGANIZADOR);
        crearSiNoExiste("Admin", "Demo", "admin@example.com", Rol.ADMINISTRADOR);
        crearSiNoExiste("Acceso", "Demo", "acceso@example.com", Rol.PERSONAL_ACCESO);
    }

    private void crearSiNoExiste(String nombre, String apellido, String email, Rol rol) {
        if (repo.existsByEmail(email)) return;
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setEmail(email);
        u.setPassword(encoder.encode("1234")); // solo datos de desarrollo
        u.setRol(rol);
        repo.save(u);
    }
}