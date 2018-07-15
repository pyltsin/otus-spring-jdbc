package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    List<Author> getByBook(long id);

    Author find(long id);

    Author insert(Author author);

    Author update(Author author);

    void delete(Author author);
}
