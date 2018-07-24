package com.otus.jdbc.services;

import com.otus.jdbc.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    List<Book> getByAuthor(int id);

    Book insert(Book book, List<Integer> authorIds);

    Book update(int idBook, List<Integer> authorIds);

    Book update(Book book);

    void delete(int id);

    Book get(int id);
}
