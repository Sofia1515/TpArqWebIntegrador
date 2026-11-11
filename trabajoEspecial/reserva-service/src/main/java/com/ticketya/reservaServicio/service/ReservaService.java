package com.ticketya.reservaServicio.service;

import com.ticketya.reservaServicio.cliente.EventoClient;
import com.ticketya.reservaServicio.dto.*;
import com.ticketya.reservaServicio.models.*;
import com.ticketya.reservaServicio.repository.CompraRepository;
import com.ticketya.reservaServicio.repository.EntradaRepository;
import com.ticketya.reservaServicio.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CompraRepository compraRepository;
    private final EntradaRepository entradaRepository;
    @Autowired
    private EventoClient eventoClient;

    public ReservaService(ReservaRepository reservaRepository,
                          CompraRepository compraRepository,
                          EntradaRepository entradaRepository) {
        this.reservaRepository = reservaRepository;
        this.compraRepository = compraRepository;
        this.entradaRepository = entradaRepository;
    }

    @Transactional
    public ReservaDTO crearReserva(CrearReservaDTO dto) {
        // 1. VALIDACIÓN VÍA OPENFEIGN: Verificar que el evento exista y esté PUBLICADO
        EventoDTO evento = eventoClient.obtenerEventoPorId(dto.getIdEvento());
        if (evento == null || !"PUBLICADO".equalsIgnoreCase(evento.getEstado())) {
            throw new RuntimeException("El evento no existe o no se encuentra PUBLICADO para recibir reservas.");
        }
        List<EstadoReserva> estadosOcupados = List.of(EstadoReserva.PENDIENTE, EstadoReserva.CONFIRMADA);

        Optional<Reserva> reservaExistente = reservaRepository
                .findByIdEventoAndIdSectorAndFilaAndAsientoAndEstadoReservaIn(
                        dto.getIdEvento(), dto.getIdSector(), dto.getFila(), dto.getAsiento(), estadosOcupados);

        if (reservaExistente.isPresent()) {
            throw new RuntimeException("El asiento ya está ocupado o reservado.");
        }

        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setIdUsuario(dto.getIdUsuario());
        nuevaReserva.setIdEvento(dto.getIdEvento());
        nuevaReserva.setIdSector(dto.getIdSector());
        nuevaReserva.setFila(dto.getFila());
        nuevaReserva.setAsiento(dto.getAsiento());
        nuevaReserva.setFechaCreacion(LocalDateTime.now());
        nuevaReserva.setFechaVencimiento(LocalDateTime.now().plusMinutes(10));
        nuevaReserva.setEstadoReserva(EstadoReserva.PENDIENTE);

        try {
            Reserva reservaGuardada = reservaRepository.saveAndFlush(nuevaReserva);
            return convertirADTO(reservaGuardada);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("El asiento ya está ocupado o reservado por otro usuario en simultáneo.");
        }
    }

    @Transactional
    public CompraDTO confirmarCompra(Long reservaId, String medioDePago) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // 2. PRECIO REAL VÍA OPENFEIGN: Obtener el precio configurado en el sector del evento
        EventoDTO evento = eventoClient.obtenerEventoPorId(reserva.getIdEvento());
        Double precioReal = 0.0;
        if (evento != null && evento.getSectores() != null) {
            precioReal = evento.getSectores().stream()
                    .filter(s -> s.getId().equals(reserva.getIdSector()))
                    .findFirst()
                    .map(SectorDTO::getPrecio)
                    .orElse(1500.0); // Valor por defecto si no lo encuentra
        }

        reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
        reservaRepository.save(reserva);

        Compra compra = new Compra();
        compra.setIdUsuario(reserva.getIdUsuario());
        compra.setIdEvento(reserva.getIdEvento());
        compra.setMontoTotal(precioReal);
        compra.setMedioDePago(medioDePago);
        compra.setFechaCompra(LocalDateTime.now());
        Compra compraGuardada = compraRepository.save(compra);

        Entrada entrada = new Entrada();
        entrada.setCompra(compraGuardada);
        entrada.setIdSector(reserva.getIdSector());
        entrada.setFila(reserva.getFila());
        entrada.setAsiento(reserva.getAsiento());
        entrada.setCodigoQR("QR-" + UUID.randomUUID().toString());
        entrada.setEstadoEntrada(EstadoEntrada.NO_USADA);
        Entrada entradaGuardada = entradaRepository.save(entrada);

        return convertirCompraADTO(compraGuardada);
    }

    @Transactional
    public void liberarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setEstadoReserva(EstadoReserva.EXPIRADA);
        reservaRepository.save(reserva);
    }

    private ReservaDTO convertirADTO(Reserva reserva) {
        return new ReservaDTO(
                reserva.getId(),
                reserva.getIdUsuario(),
                reserva.getIdEvento(),
                reserva.getIdSector(),
                reserva.getFila(),
                reserva.getAsiento(),
                reserva.getFechaCreacion(),
                reserva.getFechaVencimiento(),
                reserva.getEstadoReserva()
        );
    }

    private CompraDTO convertirCompraADTO(Compra compra) {
        List<EntradaDTO> entradasDTO = new ArrayList<>();

        if (compra.getEntradas() != null) {
            for (Entrada e : compra.getEntradas()) {
                entradasDTO.add(new EntradaDTO(
                        e.getId(),
                        e.getIdSector(),
                        e.getFila(),
                        e.getAsiento(),
                        e.getCodigoQR(),
                        e.getEstadoEntrada()
                ));
            }
        }

        return new CompraDTO(
                compra.getId(),
                compra.getIdUsuario(),
                compra.getIdEvento(),
                compra.getMontoTotal(),
                compra.getMedioDePago(),
                compra.getFechaCompra(),
                entradasDTO
        );
    }
}