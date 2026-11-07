package com.ticketya.eventoservicio.repository;

import com.ticketya.eventoservicio.models.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    // busca todos los sectores que pertenecen a un evento
    List<Sector> findByEventoId(Long idEvento);
}
