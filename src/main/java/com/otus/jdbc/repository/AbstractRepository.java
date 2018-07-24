package com.otus.jdbc.repository;

import java.util.List;

public interface AbstractRepository<T> {
    T save(T entity);

    T update(T entity);

    void delete(T entity);

    List<T> getAll();

    T getById(int id);

    T getReference(int id);
}
