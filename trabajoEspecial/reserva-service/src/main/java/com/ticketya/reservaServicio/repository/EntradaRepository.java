package com.ticketya.reservaServicio.repository;

import com.ticketya.reservaServicio.models.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    Optional<Entrada> findByCodigoQR(String codigoQR);

    List<Entrada> findByCompraIdEvento(Long idEvento);
}