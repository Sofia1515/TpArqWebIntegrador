package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.entities.Matricula;
import org.example.factory.MySQLFactory;
import org.example.repository.MatriculaRepository;
import org.example.dto.ReporteCarreraDTO;
import java.util.List;
import java.util.ArrayList;

public class MatriculaRepositoryImpl implements MatriculaRepository {

    private static MatriculaRepositoryImpl instance;

    private MatriculaRepositoryImpl() {}

    public static synchronized MatriculaRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new MatriculaRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void guardar(Matricula matricula) {
        EntityManager em = MySQLFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(matricula);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

//    @Override
//    public List obtenerInscriptosPorCarreraYAnio() {
//        EntityManager em = MySQLFactory.getEntityManager();
//        String jpql =   "SELECT m.carrera.nombre, m.inscripcion, COUNT(m) " +
//                        "FROM Matricula m " +
//                        "GROUP BY m.carrera.nombre, m.inscripcion " +
//                        "ORDER BY m.carrera.nombre ASC, m.inscripcion ASC";
//
//        try {
//            return em.createQuery(jpql, Object[].class).getResultList();
//        } finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public List obtenerEgresadosPorCarreraYAnio() {
//        EntityManager em = MySQLFactory.getEntityManager();
//
//        String jpql = "SELECT m.carrera.nombre, m.graduacion, COUNT(m) " +
//                    "FROM Matricula m " +
//                    "WHERE m.graduacion > 0 " +
//                    "GROUP BY m.carrera.nombre, m.graduacion " +
//                    "ORDER BY m.carrera.nombre ASC, m.graduacion ASC";
//
//        try {
//            return em.createQuery(jpql, Object[].class).getResultList();
//        } finally {
//            em.close();
//        }
//    }

    @Override
    public List<ReporteCarreraDTO> obtenerReporteCarreras() {

        EntityManager em = MySQLFactory.getEntityManager();

        try {
            // Inscriptos por carrera y año
            String jpqlInscriptos =
                    "SELECT m.carrera.nombre, m.inscripcion, COUNT(m) " +
                            "FROM Matricula m " +
                            "GROUP BY m.carrera.nombre, m.inscripcion " +
                            "ORDER BY m.carrera.nombre ASC, m.inscripcion ASC";

            List<Object[]> inscriptos =
                    em.createQuery(jpqlInscriptos, Object[].class).getResultList();


            // Egresados por carrera y año
            String jpqlEgresados =
                    "SELECT m.carrera.nombre, m.graduacion, COUNT(m) " +
                            "FROM Matricula m " +
                            "WHERE m.graduacion > 0 " +
                            "GROUP BY m.carrera.nombre, m.graduacion " +
                            "ORDER BY m.carrera.nombre ASC, m.graduacion ASC";

            List<Object[]> egresados =
                    em.createQuery(jpqlEgresados, Object[].class).getResultList();


            List<ReporteCarreraDTO> resultado = new ArrayList<>();

            // Primero agregamos los inscriptos
            for (Object[] fila : inscriptos) {

                String carrera = (String) fila[0];
                int anio = (int) fila[1];
                long cantidadInscriptos = (long) fila[2];

                long cantidadEgresados = 0;

                // Buscamos si existe un egresado para
                // la misma carrera y el mismo año
                for (Object[] filaEgresado : egresados) {

                    String carreraEgresado = (String) filaEgresado[0];
                    int anioEgresado = (int) filaEgresado[1];

                    if (carrera.equals(carreraEgresado) &&
                            anio == anioEgresado) {

                        cantidadEgresados = (long) filaEgresado[2];
                    }
                }

                resultado.add(
                        new ReporteCarreraDTO(
                                carrera,
                                anio,
                                cantidadInscriptos,
                                cantidadEgresados
                        )
                );
            }


            // Agregamos los años que tienen egresados
            // pero que no tienen inscriptos
            for (Object[] filaEgresado : egresados) {

                String carrera = (String) filaEgresado[0];
                int anio = (int) filaEgresado[1];
                long cantidadEgresados = (long) filaEgresado[2];

                boolean existe = false;

                for (Object[] filaInscripto : inscriptos) {

                    String carreraInscripto = (String) filaInscripto[0];
                    int anioInscripto = (int) filaInscripto[1];

                    if (carrera.equals(carreraInscripto) &&
                            anio == anioInscripto) {

                        existe = true;
                    }
                }

                if (!existe) {
                    resultado.add(
                            new ReporteCarreraDTO(
                                    carrera,
                                    anio,
                                    0,
                                    cantidadEgresados
                            )
                    );
                }
            }

            // Orden final: carrera alfabéticamente y año cronológicamente
            resultado.sort((a, b) -> {

                int comparacionCarrera =
                        a.getCarrera().compareTo(b.getCarrera());

                if (comparacionCarrera != 0) {
                    return comparacionCarrera;
                }

                return Integer.compare(a.getAnio(), b.getAnio());
            });

            return resultado;

        } finally {
            em.close();
        }
    }

}