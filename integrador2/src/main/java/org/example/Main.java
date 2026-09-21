package org.example;

import org.example.entities.Estudiante;
import org.example.factory.Factory;
import org.example.repository.EstudianteRepository;
import org.example.utils.Helper;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Factory factory = Factory.getFactory(Factory.MYSQL);
        Helper helper = new Helper(factory);

        try {
            helper.populateDB();

            EstudianteRepository estudianteRepo = factory.getEstudianteRepository();

            // --- INCISO C: Obtener todos los estudiantes ordenados ---
            System.out.println("\n--- INCISO C: Estudiantes ordenados por apellido ---");
            List<Estudiante> estudiantesOrdenados = estudianteRepo.obtenerTodosOrdenados();
            for (Estudiante e : estudiantesOrdenados) {
                System.out.println(e.getApellido() + ", " + e.getNombre() + " - LU: " + e.getLibretaUniversitaria());
            }

            // --- INCISO D: Buscar por Libreta Universitaria ---
            System.out.println("\n--- INCISO D: Buscar por LU ---");
            Estudiante estPorLu = estudianteRepo.buscarPorLibreta(34978);
            if (estPorLu != null) {
                System.out.println("Estudiante encontrado: " + estPorLu.getNombre() + " " + estPorLu.getApellido() + " (DNI: " + estPorLu.getDni() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}