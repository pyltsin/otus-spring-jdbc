package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    List<Book> getByAuthor(long id);

    Book insert(Book book);

    Book update(Book book);

    void insertToAutor(Book book, Author author);

    void delete(Book book);

    Book find(Long id);
}
