package org.example.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.repository.CarreraRepository;
import org.example.repository.EstudianteRepository;
import org.example.repository.MatriculaRepository;
import org.example.repository.impl.CarreraRepositoryImpl;
import org.example.repository.impl.EstudianteRepositoryImpl;
import org.example.repository.impl.MatriculaRepositoryImpl;

public class MySQLFactory extends Factory {

    private static EntityManagerFactory emf;
    private static MySQLFactory instance;

    private MySQLFactory() {

        emf = Persistence.createEntityManagerFactory("integrador2");
    }

    public static Factory getInstance() {
        if (instance == null) {
            instance = new MySQLFactory();
        }
        return instance;
    }

    public static EntityManager getEntityManager() {
        getInstance();
        return emf.createEntityManager();
    }

    @Override
    public EstudianteRepository getEstudianteRepository() {
        return EstudianteRepositoryImpl.getInstance();
    }

    @Override
    public MatriculaRepository getMatriculaRepository() {
        return MatriculaRepositoryImpl.getInstance();
    }

    @Override
    public CarreraRepository getCarreraRepository() {
        return CarreraRepositoryImpl.getInstance();
    }
}