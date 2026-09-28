package org.example;

import org.example.dto.CarreraDTO;
import org.example.dto.ReporteCarreraDTO;
import org.example.entities.Estudiante;
import org.example.factory.Factory;
import org.example.repository.EstudianteRepository;
import org.example.utils.Helper;
import org.example.repository.MatriculaRepository;
import org.example.dto.EstudianteDTO;

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

            List<EstudianteDTO> estudiantesOrdenados =
                    estudianteRepo.obtenerTodosOrdenados();

            for (EstudianteDTO e : estudiantesOrdenados) {
                System.out.println(
                        e.getApellido() + ", " +
                                e.getNombre() +
                                " - LU: " +
                                e.getLibretaUniversitaria()
                );
            }

            // --- INCISO D: Buscar por Libreta Universitaria ---
            System.out.println("\n--- INCISO D: Buscar por LU ---");

            EstudianteDTO estPorLu =
                    estudianteRepo.buscarPorLibreta(34978);

            if (estPorLu != null) {
                System.out.println(
                        "Estudiante encontrado: " +
                                estPorLu.getNombre() + " " +
                                estPorLu.getApellido()
                );
            }

            // --- INCISO E: Buscar por género ---
            System.out.println("\n--- INCISO E: Estudiantes de género F ---");

            List<EstudianteDTO> estudiantesF =
                    estudianteRepo.buscarPorGenero("Female");

            if (estudiantesF != null) {
                for (EstudianteDTO e : estudiantesF) {
                    System.out.println(
                            e.getApellido() + ", " +
                                    e.getNombre() +
                                    " - Género: " +
                                    e.getGenero()
                    );
                }
            }

            System.out.println("\n--- INCISO E: Estudiantes de género M ---");

            List<EstudianteDTO> estudiantesM =
                    estudianteRepo.buscarPorGenero("Male");

            if (estudiantesM != null) {
                for (EstudianteDTO e : estudiantesM) {
                    System.out.println(
                            e.getApellido() + ", " +
                                    e.getNombre() +
                                    " - Género: " +
                                    e.getGenero()
                    );
                }
            }

            // --- INCISO F: Carreras que tengan estudiantes inscriptos y ordenada por cantidad ---
            System.out.println("\n--- INCISO F: Carreras con cantidad de inscriptos ---");

            List<CarreraDTO> carrerasConInscriptos =
                    factory.getCarreraRepository()
                            .obtenerCarrerasConCantidadInscriptos();

            for (CarreraDTO dto : carrerasConInscriptos) {
                System.out.println(
                        dto.getNombreCarrera() +
                                " → " +
                                dto.getCantidadInscriptos()
                );
            }

            // --- INCISO G: Estudiantes de una carrera filtrado por ciudad ---
            System.out.println(
                    "\n--- INCISO G: Estudiantes de una carrera filtrado por ciudad ---"
            );

            List<EstudianteDTO> estudiantesFiltrados =
                    estudianteRepo.buscarPorCarreraYCiudad(
                            "TUDAI",
                            "Rauch"
                    );

            if (estudiantesFiltrados == null ||
                    estudiantesFiltrados.isEmpty()) {

                System.out.println(
                        "No se encontraron estudiantes con esos criterios."
                );

            } else {

                for (EstudianteDTO dto : estudiantesFiltrados) {
                    System.out.println(dto);
                }
            }

            // --- REPORTE DE CARRERAS ---
            System.out.println("\n--- REPORTE DE CARRERAS ---");
            System.out.println("Carrera | Año | Inscriptos | Egresados");

            MatriculaRepository matriculaRepo =
                    factory.getMatriculaRepository();

            List<ReporteCarreraDTO> reporte =
                    matriculaRepo.obtenerReporteCarreras();

            for (ReporteCarreraDTO fila : reporte) {
                System.out.println(
                        fila.getCarrera() + " | " +
                                fila.getAnio() + " | " +
                                fila.getInscriptos() + " | " +
                                fila.getEgresados()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}