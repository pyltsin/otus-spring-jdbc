package com.otus.jdbc.dao;

import com.otus.jdbc.model.Book;

import java.util.List;

public interface BookDao extends AbstractDao<Book> {

    List<Book> findByAuthor(long id);
}
