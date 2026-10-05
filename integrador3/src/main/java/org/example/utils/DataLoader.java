package org.example.config;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entity.Carrera;
import org.example.entity.Estudiante;
import org.example.entity.Matricula;
import org.example.repository.CarreraRepository;
import org.example.repository.EstudianteRepository;
import org.example.repository.MatriculaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
public class DataLoader implements CommandLineRunner {

    private final CarreraRepository carreraRepository;
    private final EstudianteRepository estudianteRepository;
    private final MatriculaRepository matriculaRepository;

    public DataLoader(
            CarreraRepository carreraRepository,
            EstudianteRepository estudianteRepository,
            MatriculaRepository matriculaRepository) {

        this.carreraRepository = carreraRepository;
        this.estudianteRepository = estudianteRepository;
        this.matriculaRepository = matriculaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        populateCarreras("carreras.csv");
        populateEstudiantes("estudiantes.csv");
        populateMatriculas("estudianteCarrera.csv");

        System.out.println("Base de datos poblada exitosamente desde los CSV.");
    }

    private void populateCarreras(String fileName) throws Exception {

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

             Reader reader = new InputStreamReader(is);

             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)) {

            for (CSVRecord row : parser) {

                int id = Integer.parseInt(row.get("id_carrera").trim());
                String nombre = row.get("carrera").trim();
                int duracion = Integer.parseInt(row.get("duracion").trim());

                carreraRepository.save(
                        new Carrera(id, nombre, duracion)
                );
            }
        }
    }

    private void populateEstudiantes(String fileName) throws Exception {

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

             Reader reader = new InputStreamReader(is);

             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)) {

            for (CSVRecord row : parser) {

                int dni = Integer.parseInt(row.get("DNI").trim());
                String nombre = row.get("nombre").trim();
                String apellido = row.get("apellido").trim();
                int edad = Integer.parseInt(row.get("edad").trim());
                String genero = row.get("genero").trim();
                String ciudad = row.get("ciudad").trim();
                int lu = Integer.parseInt(row.get("LU").trim());

                estudianteRepository.save(
                        new Estudiante(
                                dni,
                                nombre,
                                apellido,
                                edad,
                                genero,
                                ciudad,
                                lu
                        )
                );
            }
        }
    }

    private void populateMatriculas(String fileName) throws Exception {

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

             Reader reader = new InputStreamReader(is);

             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)) {

            for (CSVRecord row : parser) {

                int id = Integer.parseInt(row.get("id").trim());
                int idEstudiante = Integer.parseInt(
                        row.get("id_estudiante").trim()
                );
                int idCarrera = Integer.parseInt(
                        row.get("id_carrera").trim()
                );

                int anioInscripcion = Integer.parseInt(
                        row.get("inscripcion").trim()
                );

                int anioGraduacion = Integer.parseInt(
                        row.get("graduacion").trim()
                );

                int antiguedad = Integer.parseInt(
                        row.get("antiguedad").trim()
                );

                Estudiante estudiante =
                        estudianteRepository.findById(idEstudiante)
                                .orElse(null);

                Carrera carrera =
                        carreraRepository.findById(idCarrera)
                                .orElse(null);

                if (estudiante != null && carrera != null) {

                    Matricula matricula = new Matricula(
                            id,
                            anioInscripcion,
                            anioGraduacion,
                            antiguedad,
                            estudiante,
                            carrera
                    );

                    matriculaRepository.save(matricula);
                }
            }
        }
    }
}