package com.otus.jdbc.dao;

import com.otus.jdbc.model.Ids;

import java.util.List;

public interface AbstractDao<T extends Ids> {
    T findById(long id);

    List<T> findAll();

    T insert(T author);

    T update(T author);

    void delete(T author);
}
