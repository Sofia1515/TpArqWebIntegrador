package org.example.factory;

import org.example.repository.CarreraRepository;
import org.example.repository.EstudianteRepository;
import org.example.repository.MatriculaRepository;

public abstract class Factory {

    public static final int MYSQL = 1;
    public static final int DERBY = 2;

    public static Factory getFactory(int type) {
        switch (type) {
            case MYSQL:
                return MySQLFactory.getInstance();
            case DERBY:
                throw new UnsupportedOperationException("No se ha implementado la fábrica para Derby");
            default:
                throw new IllegalArgumentException("Tipo de Factory no válido");
        }
    }

    public abstract EstudianteRepository getEstudianteRepository();
    public abstract MatriculaRepository getMatriculaRepository();
    public abstract CarreraRepository getCarreraRepository();
}