package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.entities.Matricula;
import org.example.factory.MySQLFactory;
import org.example.repository.MatriculaRepository;

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
}