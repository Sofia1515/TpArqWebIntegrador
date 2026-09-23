package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.entities.Matricula;
import org.example.factory.MySQLFactory;
import org.example.repository.MatriculaRepository;
import java.util.List;

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

    @Override
    public List obtenerInscriptosPorCarreraYAnio() {
        EntityManager em = MySQLFactory.getEntityManager();
        String jpql =   "SELECT m.carrera.nombre, m.inscripcion, COUNT(m) " +
                        "FROM Matricula m " +
                        "GROUP BY m.carrera.nombre, m.inscripcion " +
                        "ORDER BY m.carrera.nombre ASC, m.inscripcion ASC";

        try {
            return em.createQuery(jpql, Object[].class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List obtenerEgresadosPorCarreraYAnio() {
        EntityManager em = MySQLFactory.getEntityManager();

        String jpql = "SELECT m.carrera.nombre, m.graduacion, COUNT(m) " +
                    "FROM Matricula m " +
                    "WHERE m.graduacion > 0 " +
                    "GROUP BY m.carrera.nombre, m.graduacion " +
                    "ORDER BY m.carrera.nombre ASC, m.graduacion ASC";

        try {
            return em.createQuery(jpql, Object[].class).getResultList();
        } finally {
            em.close();
        }
    }

}