package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    List<Author> getByBook(int id);

    Author find(int id);

    Author insert(Author author);

    Author update(Author author);

    void delete(int id);
}
