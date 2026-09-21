package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.Carrera;
import org.example.entities.Estudiante;
import org.example.entities.Matricula;
import org.example.factory.Factory;
import org.example.repository.CarreraRepository;
import org.example.repository.EstudianteRepository;
import org.example.repository.MatriculaRepository;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Helper {

    private final Factory factory;

    public Helper(Factory factory) {
        this.factory = factory;
    }

    public void populateDB() throws Exception {
        populateCarreras("carreras.csv");
        populateEstudiantes("estudiantes.csv");
        populateMatriculas("estudianteCarrera.csv");
        System.out.println("Base de datos poblada exitosamente desde los CSV.");
    }

    private void populateCarreras(String fileName) throws Exception {
        CarreraRepository carreraRepo = factory.getCarreraRepository();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             Reader reader = new InputStreamReader(is);
             CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {

            for (CSVRecord row : parser) {
                int id = Integer.parseInt(row.get("id_carrera").trim());
                String nombre = row.get("carrera").trim();
                int duracion = Integer.parseInt(row.get("duracion").trim());

                carreraRepo.guardar(new Carrera(id, nombre, duracion));
            }
        }
    }

    private void populateEstudiantes(String fileName) throws Exception {
        EstudianteRepository estudianteRepo = factory.getEstudianteRepository();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             Reader reader = new InputStreamReader(is);
             CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {

            for (CSVRecord row : parser) {
                int dni = Integer.parseInt(row.get("DNI").trim());
                String nombre = row.get("nombre").trim();
                String apellido = row.get("apellido").trim();
                int edad = Integer.parseInt(row.get("edad").trim());
                String genero = row.get("genero").trim();
                String ciudad = row.get("ciudad").trim();
                int lu = Integer.parseInt(row.get("LU").trim());

                estudianteRepo.guardar(new Estudiante(dni, nombre, apellido, edad, genero, ciudad, lu));
            }
        }
    }

    private void populateMatriculas(String fileName) throws Exception {
        MatriculaRepository matriculaRepo = factory.getMatriculaRepository();
        EstudianteRepository estudianteRepo = factory.getEstudianteRepository();
        CarreraRepository carreraRepo = factory.getCarreraRepository();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             Reader reader = new InputStreamReader(is);
             CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {

            for (CSVRecord row : parser) {
                int id = Integer.parseInt(row.get("id").trim());
                int idEstudiante = Integer.parseInt(row.get("id_estudiante").trim());
                int idCarrera = Integer.parseInt(row.get("id_carrera").trim());
                int anioInscripcion = Integer.parseInt(row.get("inscripcion").trim());
                int anioGraduacion = Integer.parseInt(row.get("graduacion").trim());
                int antiguedad = Integer.parseInt(row.get("antiguedad").trim());

                Estudiante estudiante = estudianteRepo.buscarPorDni(idEstudiante);
                Carrera carrera = carreraRepo.buscarPorId(idCarrera);

                if (estudiante != null && carrera != null) {
                    Matricula matricula = new Matricula(id, anioInscripcion, anioGraduacion, antiguedad, estudiante, carrera);
                    matriculaRepo.guardar(matricula);
                }
            }
        }
    }
}