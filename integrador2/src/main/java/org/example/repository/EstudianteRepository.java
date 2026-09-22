package org.example.repository;

import org.example.entities.Estudiante;

import java.util.List;

public interface EstudianteRepository {

    // Insertar (Guardar)
    void guardar(Estudiante estudiante);

    // Buscar por clave primaria (DNI)
    Estudiante buscarPorDni(int dni);

    // c) Recuperar todos los estudiantes ordenados por un criterio (ej. por apellido)
    List<Estudiante> obtenerTodosOrdenados();

    // d) Obtener un estudiante en base a su numero de libreta universitaria
    Estudiante buscarPorLibreta(int libretaUniversitaria);
    // e) Recuperar todos los estudiantes en base a su género
    List<Estudiante> buscarPorGenero(String genero);

}