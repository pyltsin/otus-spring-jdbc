package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author get(int id);

    Author insert(Author author);

    Author update(Author author);

    void delete(int id);

    void save(Integer id, String name);
}
