package org.example;

import org.example.dto.CarreraDTO;
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


            // --- INCISO E: Buscar por género ---
            System.out.println("\n--- INCISO E: Estudiantes de género F ---");
            List<Estudiante> estudiantesF = estudianteRepo.buscarPorGenero("Female");
            if (estudiantesF != null) {
                for (Estudiante e : estudiantesF) {
                    System.out.println(e.getApellido() + ", " + e.getNombre() + " - Género: " + e.getGenero());
                }
            }

            System.out.println("\n--- INCISO E: Estudiantes de género M ---");
            List<Estudiante> estudiantesM = estudianteRepo.buscarPorGenero("Male");
            if (estudiantesM != null) {
                for (Estudiante e : estudiantesM) {
                    System.out.println(e.getApellido() + ", " + e.getNombre() + " - Género: " + e.getGenero());
                }
            }


            // --- INCISO F: Carreras que tengan estudiantes inscriptos y ordenada por cantidad.
            System.out.println("\n--- INCISO F: Carreras con cantidad de inscriptos ---");
            List<CarreraDTO> carrerasConInscriptos = factory.getCarreraRepository().obtenerCarrerasConCantidadInscriptos();
            for (CarreraDTO dto : carrerasConInscriptos) {
                System.out.println(dto.getNombreCarrera() + " → " + dto.getCantidadInscriptos());
            }

            // --- INCISO G: Estudiantes de una carrera filtrado por ciudad ---
            System.out.println("\n--- INCISO G: Estudiantes de una carrera filtrado por ciudad ---");
            List<EstudianteDTO> estudiantesFiltrados = estudianteRepo.buscarPorCarreraYCiudad("Abogacia", "Idvor");            if (estudiantesFiltrados == null || estudiantesFiltrados.isEmpty()) {
                System.out.println("No se encontraron estudiantes con esos criterios.");
            } else {
                for (EstudianteDTO dto : estudiantesFiltrados) {
                    System.out.println(dto);
                }
            }


            // --- INCISO 3: Inscriptos por carrera y año ---
            System.out.println("\n--- INCISO 3: Inscriptos por carrera y año ---");
            MatriculaRepository matriculaRepo = factory.getMatriculaRepository();
            List inscriptos = matriculaRepo.obtenerInscriptosPorCarreraYAnio();
            for (Object[] fila : (List<Object[]>) inscriptos) {
                System.out.println(
                    fila[0] + " | " +
                    fila[1] + " | " +
                    fila[2]
                );
            }

            System.out.println("\n--- INCISO 3: Egresados por carrera y año ---");
            List egresados = matriculaRepo.obtenerEgresadosPorCarreraYAnio();
            for (Object[] fila : (List<Object[]>) egresados) {
                System.out.println(
                    fila[0] + " | " +
                    fila[1] + " | " +
                    fila[2]
                );
            }

            System.out.println("\n--- REPORTE DE CARRERAS ---");
            System.out.println("Carrera | Año | Inscriptos | Egresados");
            List inscriptosReporte = matriculaRepo.obtenerInscriptosPorCarreraYAnio();
            List egresadosReporte = matriculaRepo.obtenerEgresadosPorCarreraYAnio();
            
           for (Object[] inscripto : (List<Object[]>) inscriptosReporte) {
                String carrera = (String) inscripto[0];
                int anio = (int) inscripto[1];
                long cantidadInscriptos = (long) inscripto[2];

                long cantidadEgresados = 0;

                for (Object[] egresado : (List<Object[]>) egresadosReporte) {

                    String carreraEgresado = (String) egresado[0];
                    int anioEgresado = (int) egresado[1];

                    if (carrera.equals(carreraEgresado) && anio == anioEgresado) {
                        cantidadEgresados = (long) egresado[2];
                    }
                }

                System.out.println( carrera + " | " + anio + " | " + cantidadInscriptos + " | " + cantidadEgresados);
            }

            for (Object[] egresado : (List<Object[]>) egresadosReporte) {
                String carrera = (String) egresado[0];
                int anio = (int) egresado[1];
                long cantidadEgresados = (long) egresado[2];
                boolean existe = false;

                for (Object[] inscripto : (List<Object[]>) inscriptosReporte) {
                    String carreraInscripto = (String) inscripto[0];
                    int anioInscripto = (int) inscripto[1];
                    if (carrera.equals(carreraInscripto) && anio == anioInscripto) {
                        existe = true;
                    }
                }

                if (!existe) {
                    System.out.println( carrera + " | " + anio + " | " + 0 + " | " + cantidadEgresados);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}