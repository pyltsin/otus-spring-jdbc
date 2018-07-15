package com.otus.jdbc.dao;

import java.util.List;

public interface AuthorToBookDao {
    void insert(long idBook, long idAuthor);

    void deleteByBook(long idBook);

    void deleteByAuthor(long idAuthor);

    List<Long> getBookByAuthor(long id);

    List<Long> getAuthorByBook(long id);
}
