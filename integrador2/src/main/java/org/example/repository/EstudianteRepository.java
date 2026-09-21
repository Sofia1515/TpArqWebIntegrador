package org.example.repository;

import org.example.entities.Estudiante;

public interface EstudianteRepository {

    // Insertar (Guardar)
    void guardar(Estudiante estudiante);

    // Buscar por clave primaria (DNI)
    Estudiante buscarPorDni(int dni);


}