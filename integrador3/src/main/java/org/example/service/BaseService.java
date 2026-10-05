package org.example.service;

import java.util.List;

public interface BaseService<E>{
    public List<E> findAll() throws Exception;

    public E buscarPorDni(int id) throws Exception;

    public E guardar(E entity) throws Exception;

    public E update(Long id, E entity) throws Exception;

    public boolean delete(Long id) throws Exception;
}
