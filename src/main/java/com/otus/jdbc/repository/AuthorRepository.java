package com.otus.jdbc.repository;

import com.otus.jdbc.model.Author;

import java.util.List;

public interface AuthorRepository extends AbstractRepository<Author> {
    List<Author> getByBookId(int id);
}
