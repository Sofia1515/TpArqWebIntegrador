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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

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
    private final EventoClient eventoClient; // <-- Lo pasamos al constructor
    private final FilaVirtualService filaVirtualService;

    public ReservaService(ReservaRepository reservaRepository,
                          CompraRepository compraRepository,
                          EntradaRepository entradaRepository,
                          EventoClient eventoClient,
                          FilaVirtualService filaVirtualService) {
        this.reservaRepository = reservaRepository;
        this.compraRepository = compraRepository;
        this.entradaRepository = entradaRepository;
        this.eventoClient = eventoClient;
        this.filaVirtualService = filaVirtualService;
    }

    @Transactional
    public ReservaDTO crearReserva(CrearReservaDTO dto) {
        // 1. VALIDACIÓN VÍA OPENFEIGN
        EventoDTO evento = eventoClient.obtenerEventoPorId(dto.getIdEvento());
        if (evento == null || !"PUBLICADO".equalsIgnoreCase(evento.getEstado())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El evento no existe o no se encuentra PUBLICADO para recibir reservas.");
        }

        List<EstadoReserva> estadosOcupados = List.of(EstadoReserva.PENDIENTE, EstadoReserva.CONFIRMADA);

        Optional<Reserva> reservaExistente = reservaRepository
                .findByIdEventoAndIdSectorAndFilaAndAsientoAndEstadoReservaIn(
                        dto.getIdEvento(), dto.getIdSector(), dto.getFila(), dto.getAsiento(), estadosOcupados);

        if (reservaExistente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El asiento ya está ocupado o reservado.");
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

        // Si el evento es de alta demanda, debe tener autorización de la fila.
        if (evento.isAltaDemanda()
                && !filaVirtualService.estaHabilitado(
                dto.getIdEvento(), dto.getIdUsuario())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Todavía no estás habilitado para comprar. Debés esperar tu turno en la fila virtual."
            );
        }

        try {
            Reserva reservaGuardada = reservaRepository.saveAndFlush(nuevaReserva);

            // Si el evento requiere fila virtual, consumimos la habilitación.
            if (evento.isAltaDemanda()) {
                filaVirtualService.consumirHabilitacion(
                        dto.getIdEvento(), dto.getIdUsuario());
            }

            return convertirADTO(reservaGuardada);
        } catch (DataIntegrityViolationException e) {
            // Si dos hilos pasan la validación previa, solo uno llega al saveAndFlush
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El asiento ya está ocupado o reservado por otro usuario en simultáneo.");
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

    @Transactional
    public void solicitarDevolucion(Long idEntrada, Long idUsuario) {
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(() -> new RuntimeException("La entrada con ID " + idEntrada + " no existe."));

        // verificamos que la entrada sea de ese dueno
        if (!entrada.getCompra().getIdUsuario().equals(idUsuario)) {
            throw new RuntimeException("No tenes permisos para devolver esta entrada.");
        }

        // verificamos que la entrada no haya sido usada ni devuelta
        if (entrada.getEstadoEntrada() == EstadoEntrada.USADA) {
            throw new RuntimeException("Esta entrada no se puede devolver porque ya ha sido utilizada.");
        }

        if (entrada.getEstadoEntrada() == EstadoEntrada.DEVUELTA || entrada.getEstadoEntrada() == EstadoEntrada.PENDIENTE_DEVOLUCION) {
            throw new RuntimeException("Esta entrada ya tiene una devolucion en curso o fue devuelta.");
        }

        // obtenemos la fecha del evento via OpenFeign desde evento-servicio
        EventoDTO evento = eventoClient.obtenerEventoPorId(entrada.getCompra().getIdEvento());
        if (evento == null) {
            throw new RuntimeException("No se pudo obtener la informacion de ese evento.");
        }

        // validamos que falten mas de 72 hs para el evento
        LocalDateTime fechaLimite = LocalDateTime.now().plusHours(72);
        if (fechaLimite.isAfter(evento.getFechaHora())) {
            throw new RuntimeException("Las devoluciones solo se permiten minimo 72 hs de anticipacion al evento.");
        }

        entrada.setEstadoEntrada(EstadoEntrada.PENDIENTE_DEVOLUCION);
        entradaRepository.save(entrada);
    }

    @Transactional
    public void aprobarDevolucion(Long idEntrada) {
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(() -> new RuntimeException("La entrada con ID " + idEntrada + " no existe."));

        if (entrada.getEstadoEntrada() != EstadoEntrada.PENDIENTE_DEVOLUCION) {
            throw new RuntimeException("La entrada no tiene una solicitud de devolucion pendiente.");
        }

        entrada.setEstadoEntrada(EstadoEntrada.DEVUELTA);
        entradaRepository.save(entrada);

        // Opcional: Aca iria la llamada a la pasarela de pagos para el reintegro
    }

    @Transactional
    public void rechazarDevolucion(Long idEntrada) {
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(() -> new RuntimeException("La entrada con ID " + idEntrada + " no existe."));

        if (entrada.getEstadoEntrada() != EstadoEntrada.PENDIENTE_DEVOLUCION) {
            throw new RuntimeException("La entrada no tiene una solicitud de devolucion pendiente.");
        }

        // Volvemos la entrada a su estado normal
        entrada.setEstadoEntrada(EstadoEntrada.NO_USADA);
        entradaRepository.save(entrada);
    }

    @Transactional
    public void cancelarEntradasPorEvento(Long idEvento) {
        List<Entrada> entradas = entradaRepository.findByCompraIdEvento(idEvento);
        for (Entrada entrada : entradas) {
            if (entrada.getEstadoEntrada() == EstadoEntrada.NO_USADA || entrada.getEstadoEntrada() == EstadoEntrada.PENDIENTE_DEVOLUCION) {
                entrada.setEstadoEntrada(EstadoEntrada.DEVUELTA);
                entradaRepository.save(entrada);
            }
        }
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