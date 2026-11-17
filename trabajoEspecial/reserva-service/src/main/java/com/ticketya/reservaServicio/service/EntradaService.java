package com.ticketya.reservaServicio.service;

import com.ticketya.reservaServicio.dto.ValidarQrRequestDTO;
import com.ticketya.reservaServicio.dto.ValidarQrResponseDTO;
import com.ticketya.reservaServicio.models.Entrada;
import com.ticketya.reservaServicio.models.EstadoEntrada;
import com.ticketya.reservaServicio.repository.EntradaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EntradaService {
    private final EntradaRepository entradaRepository;

        public EntradaService(EntradaRepository entradaRepository) {
            this.entradaRepository = entradaRepository;
        }

        @Transactional
        public ValidarQrResponseDTO validarIngreso(ValidarQrRequestDTO request) {
            //Verifica que la entrada exista
            Entrada entrada = entradaRepository.findByCodigoQR(request.getCodigoQR())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Código QR inexistente."));

            //Verifica que corresponda al evento
            if (!entrada.getCompra().getIdEvento().equals(request.getIdEvento())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La entrada no corresponde a este evento.");
            }

            //Verifica que no haya sido usada
            if (entrada.getEstadoEntrada() != EstadoEntrada.NO_USADA) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "La entrada no es válida para ingresar. Estado actual: " + entrada.getEstadoEntrada());
            }

            //La marca como utilizada
            entrada.setEstadoEntrada(EstadoEntrada.USADA);
            entradaRepository.save(entrada);

            return new ValidarQrResponseDTO(
                    true,
                    "Ingreso habilitado",
                    entrada.getId(),
                    entrada.getIdSector(),
                    entrada.getFila(),
                    entrada.getAsiento()
            );
        }
}
