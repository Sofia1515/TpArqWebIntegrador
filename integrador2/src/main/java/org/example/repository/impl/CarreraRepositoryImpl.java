package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.entities.Carrera;
import org.example.factory.MySQLFactory;
import org.example.repository.CarreraRepository;

public class CarreraRepositoryImpl implements CarreraRepository {

    private static CarreraRepositoryImpl instance;

    // Constructor privado para el Singleton
    private CarreraRepositoryImpl() {}

    public static synchronized CarreraRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new CarreraRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void guardar(Carrera carrera) {
        EntityManager em = MySQLFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(carrera);
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
    public Carrera buscarPorId(int id) {
        EntityManager em = MySQLFactory.getEntityManager();
        Carrera carrera = null;
        try {
            carrera = em.find(Carrera.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return carrera;
    }
}
