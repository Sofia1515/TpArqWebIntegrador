package com.ticketya.eventoservicio.repository;

import com.ticketya.eventoservicio.models.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Long> {

    //metodo para buscar lugares por ciudad
    List<Lugar> findByCiudadIgnoreCase(String ciudad);
}