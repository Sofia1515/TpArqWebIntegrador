package org.example.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface RepoBase<T, ID extends Serializable> extends Repository<T, ID> {
    // Retorna un listado de todas las entidades almacenadas
    List<T> findAll();

    // Busca y devuelve una entidad segun su ID
    Optional<T> findById(ID id);

    // Persiste o actualiza una entidad en la base de datos
    T save(T persisted);

    // Elimina la entidad enviada por parametro
    void delete(T deleted);

    // Elimina una entidad directamente por su ID
    void deleteById(ID id);

    // Devuelve true si existe un registro con el ID ingresado
    boolean existsById(ID id);
}