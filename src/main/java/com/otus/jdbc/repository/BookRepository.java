package com.otus.jdbc.repository;

import com.otus.jdbc.model.Book;

import java.util.List;

public interface BookRepository extends AbstractRepository<Book> {
    List<Book> getByAuthorId(int id);
}
