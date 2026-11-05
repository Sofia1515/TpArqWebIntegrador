package repository;

import models.Usuario;
import models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    //buscar un usuario por su mail
    Optional<Usuario> findByEmail(String email);

    // verificar si ya existe un usuario registrado con ese mail
    boolean existsByEmail(String email);

    // filtrar usuarios por su rol
    List<Usuario> findByRol(Rol rol);
}