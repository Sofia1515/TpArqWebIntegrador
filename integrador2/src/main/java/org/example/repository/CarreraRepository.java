package org.example.repository;


import org.example.dto.CarreraDTO;
import org.example.entities.Carrera;

import java.util.List;

public interface CarreraRepository {

    // Insertar (Guardar)
    void guardar(Carrera carrera);

    Carrera buscarPorId(int id);

    List<CarreraDTO> obtenerCarrerasConCantidadInscriptos();
}
