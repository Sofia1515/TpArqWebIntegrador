package com.ticketya.eventoservicio.service;

import com.ticketya.eventoservicio.dto.LugarDTO;

import java.util.List;

public interface LugarService{
    LugarDTO crearLugar(LugarDTO dto);

    LugarDTO obtenerPorId(Long id);

    List<LugarDTO> obtenerTodos();

    List<LugarDTO> obtenerPorCiudad(String ciudad);
}