package org.example.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface RepoBase<T, ID extends Serializable> extends Repository<T, ID> {
    
}