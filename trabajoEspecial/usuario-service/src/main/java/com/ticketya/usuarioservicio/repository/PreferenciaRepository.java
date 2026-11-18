
package com.ticketya.usuarioservicio.repository;

import com.ticketya.usuarioservicio.models.Preferencia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PreferenciaRepository extends MongoRepository<Preferencia, String> {

    Optional<Preferencia> findByUsuarioId(Long usuarioId);
}
