package org.example.repository;


import org.example.entities.Carrera;

public interface CarreraRepository {
    // Insertar (Guardar)
    void guardar(Carrera carrera);

    Carrera buscarPorId(int id);

}
