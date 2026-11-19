
package com.ticketya.usuarioservicio.service;

import com.ticketya.usuarioservicio.cliente.EventoClient;
import com.ticketya.usuarioservicio.dto.EventoDTO;
import com.ticketya.usuarioservicio.models.Preferencia;
import com.ticketya.usuarioservicio.repository.PreferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PreferenciaService {

    private final PreferenciaRepository preferenciaRepository;


    @Autowired
    private EventoClient eventoClient;

    public PreferenciaService(PreferenciaRepository preferenciaRepository) {
        this.preferenciaRepository = preferenciaRepository;
    }


    public Preferencia guardar(Preferencia preferencia) {
        Optional<Preferencia> existente = preferenciaRepository.findByUsuarioId(preferencia.getUsuarioId());
        if (existente.isPresent()) {
            Preferencia pref = existente.get();
            pref.setGeneros(preferencia.getGeneros());
            pref.setCategorias(preferencia.getCategorias());
            pref.setArtistasFavoritos(preferencia.getArtistasFavoritos());
            return preferenciaRepository.save(pref);
        }
        return preferenciaRepository.save(preferencia);
    }

    public Optional<Preferencia> obtenerPreferencias(Long usuarioId) {
        return preferenciaRepository.findByUsuarioId(usuarioId);
    }

    public List<EventoDTO> obtenerRecomendaciones(Long usuarioId) {
        Preferencia pref = preferenciaRepository.findByUsuarioId(usuarioId).orElse(null);

        List<EventoDTO> todosLosEventos;
        try {
            todosLosEventos = eventoClient.obtenerTodosLosEventos();
        } catch (Exception e) {
            return new ArrayList<>(); // si falla Feign devuelve lista vacía sin romper el microservicio
        }

        if (pref == null || todosLosEventos == null) {
            return todosLosEventos != null ? todosLosEventos : new ArrayList<>();
        }

        List<EventoDTO> eventosRecomendados = new ArrayList<>();

        for (EventoDTO evento : todosLosEventos) {
            if (!"PUBLICADO".equalsIgnoreCase(evento.getEstado())) {
                continue;
            }

            boolean coincide = false;

            // 1. Coincidencia por Categoría
            if (pref.getCategorias() != null && evento.getTipo() != null) {
                for (String cat : pref.getCategorias()) {
                    if (cat.equalsIgnoreCase(evento.getTipo())) {
                        coincide = true;
                        break;
                    }
                }
            }

            // 2. Coincidencia por Género
            if (!coincide && pref.getGeneros() != null && evento.getTipo() != null) {
                for (String gen : pref.getGeneros()) {
                    if (gen.equalsIgnoreCase(evento.getTipo())) {
                        coincide = true;
                        break;
                    }
                }
            }

            // 3. Coincidencia por Artista favorito
            if (!coincide && pref.getArtistasFavoritos() != null && evento.getParticipantes() != null) {
                for (String artistaFav : pref.getArtistasFavoritos()) {
                    for (String participante : evento.getParticipantes()) {
                        if (artistaFav.equalsIgnoreCase(participante)) {
                            coincide = true;
                            break;
                        }
                    }
                    if (coincide) break;
                }
            }

            if (coincide) {
                eventosRecomendados.add(evento);
            }
        }

        return eventosRecomendados;
    }
}
