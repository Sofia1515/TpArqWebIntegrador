package service;

import models.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.CompraRepository;
import repository.EntradaRepository;
import repository.ReservaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CompraRepository compraRepository;
    private final EntradaRepository entradaRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          CompraRepository compraRepository,
                          EntradaRepository entradaRepository) {
        this.reservaRepository = reservaRepository;
        this.compraRepository = compraRepository;
        this.entradaRepository = entradaRepository;
    }

    @Transactional
    public Reserva crearReserva(Long idUsuario, Long idEvento, Long idSector, int fila, int asiento) {
        List<EstadoReserva> estadosOcupados = List.of(EstadoReserva.PENDIENTE, EstadoReserva.CONFIRMADA);

        Optional<Reserva> reservaExistente = reservaRepository
                .findByIdEventoAndIdSectorAndFilaAndAsientoAndEstadoReservaIn(
                        idEvento, idSector, fila, asiento, estadosOcupados);

        if (reservaExistente.isPresent()) {
            throw new RuntimeException("El asiento ya está ocupado o reservado.");
        }

        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setIdUsuario(idUsuario);
        nuevaReserva.setIdEvento(idEvento);
        nuevaReserva.setIdSector(idSector);
        nuevaReserva.setFila(fila);
        nuevaReserva.setAsiento(asiento);
        nuevaReserva.setFechaCreacion(LocalDateTime.now());
        nuevaReserva.setFechaVencimiento(LocalDateTime.now().plusMinutes(10));
        nuevaReserva.setEstadoReserva(EstadoReserva.PENDIENTE);

        return reservaRepository.save(nuevaReserva);
    }

    @Transactional
    public Compra confirmarCompra(Long reservaId, String medioDePago) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
        reservaRepository.save(reserva);

        Compra compra = new Compra();
        compra.setIdUsuario(reserva.getIdUsuario());
        compra.setIdEvento(reserva.getIdEvento());
        compra.setMontoTotal(1500.0);
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
        entradaRepository.save(entrada);

        return compraGuardada;
    }

    @Transactional
    public void liberarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setEstadoReserva(EstadoReserva.EXPIRADA);
        reservaRepository.save(reserva);
    }
}