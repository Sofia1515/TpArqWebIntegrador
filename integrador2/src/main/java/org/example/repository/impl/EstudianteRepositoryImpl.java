package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.entities.Estudiante;
import org.example.factory.MySQLFactory;
import org.example.repository.EstudianteRepository;
import org.example.dto.EstudianteDTO;

import java.util.List;

public class EstudianteRepositoryImpl implements EstudianteRepository {

    private static EstudianteRepositoryImpl instance;

    // Constructor privado para el Singleton
    private EstudianteRepositoryImpl() {}

    public static synchronized EstudianteRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new EstudianteRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void guardar(Estudiante estudiante) {
        EntityManager em = MySQLFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(estudiante);
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
    public Estudiante buscarPorDni(int dni) {
        EntityManager em = MySQLFactory.getEntityManager();
        Estudiante estudiante = null;
        try {
            estudiante = em.find(Estudiante.class, dni);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return estudiante;
    }

    @Override
    public List<Estudiante> obtenerTodosOrdenados() {
        EntityManager em = MySQLFactory.getEntityManager();
        List<Estudiante> estudiantes = null;
        try {
            String jpql = "SELECT e FROM Estudiante e ORDER BY e.apellido ASC, e.nombre ASC";
            estudiantes = em.createQuery(jpql, Estudiante.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return estudiantes;
    }

    @Override
    public Estudiante buscarPorLibreta(int libretaUniversitaria) {
        EntityManager em = MySQLFactory.getEntityManager();
        Estudiante estudiante = null;
        try {
            String jpql = "SELECT e FROM Estudiante e WHERE e.libretaUniversitaria = :libreta";
            estudiante = em.createQuery(jpql, Estudiante.class)
                    .setParameter("libreta", libretaUniversitaria)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            System.out.println("No se encontró ningún estudiante con la LU: " + libretaUniversitaria);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return estudiante;
    }
    @Override
    public List<Estudiante> buscarPorGenero(String genero) {
        EntityManager em = MySQLFactory.getEntityManager();
        List<Estudiante> estudiantes = null;
        try {
            String jpql = "SELECT e FROM Estudiante e WHERE e.genero = :genero ORDER BY e.apellido ASC, e.nombre ASC";
            estudiantes = em.createQuery(jpql, Estudiante.class)
                    .setParameter("genero", genero)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return estudiantes;
    }

    @Override
    public List<EstudianteDTO> buscarPorCarreraYCiudad(String nombreCarrera, String ciudadResidencia) {
        EntityManager em = MySQLFactory.getEntityManager();
        List<EstudianteDTO> resultado = null;
        try {
            String jpql = "SELECT new org.example.dto.EstudianteDTO(" +
                    "e.nombre, e.apellido, e.libretaUniversitaria, e.ciudadResidencia) " +
                    "FROM Estudiante e " +
                    "JOIN Matricula m ON m.estudiante = e " +
                    "JOIN m.carrera c " +
                    "WHERE c.nombre = :nombreCarrera " +
                    "AND e.ciudadResidencia = :ciudad " +
                    "ORDER BY e.apellido ASC, e.nombre ASC";

            resultado = em.createQuery(jpql, EstudianteDTO.class)
                    .setParameter("nombreCarrera", nombreCarrera)
                    .setParameter("ciudad", ciudadResidencia)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return resultado;
    }
}
