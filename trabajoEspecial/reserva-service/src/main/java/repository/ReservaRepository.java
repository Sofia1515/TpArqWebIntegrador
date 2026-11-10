package repository;

import models.Reserva;
import models.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Optional<Reserva> findByIdEventoAndIdSectorAndFilaAndAsientoAndEstadoReservaIn(
            Long idEvento,
            Long idSector,
            int fila,
            int asiento,
            List<EstadoReserva> estados
    );
}