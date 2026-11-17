
package com.ticketya.usuarioservicio.service;

import com.ticketya.usuarioservicio.models.Preferencia;
import com.ticketya.usuarioservicio.repository.PreferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PreferenciaService {

    private final PreferenciaRepository preferenciaRepository;


    @Autowired
    public PreferenciaService(PreferenciaRepository preferenciaRepository) {
        this.preferenciaRepository = preferenciaRepository;
    }

    public Optional<Preferencia> buscarPorUsuario(Long usuarioId) {
        return preferenciaRepository.findByUsuarioId(usuarioId);
    }

    public Preferencia guardar(Preferencia preferencia) {

        Optional<Preferencia> existente =
                preferenciaRepository.findByUsuarioId(preferencia.getUsuarioId());

        if (existente.isPresent()) {
            Preferencia actual = existente.get();

            actual.setGeneros(preferencia.getGeneros());
            actual.setCategorias(preferencia.getCategorias());
            actual.setArtistasFavoritos(preferencia.getArtistasFavoritos());

            return preferenciaRepository.save(actual);
        }

        return preferenciaRepository.save(preferencia);
    }
}
