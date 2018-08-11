package com.otus.jdbc.controller;

import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Genre;
import com.otus.jdbc.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@ShellComponent
public class BookController {

    @Autowired
    private BookService bookService;

    @ShellMethod(value = "find all books")
    void getAllBooks() {
        bookService.getAll().forEach(System.out::println);
    }

    @ShellMethod(value = "find books by authors id")
    void getBooksAuthor(int id) {
        bookService.getByAuthor(id).forEach(System.out::println);
    }

    @ShellMethod(value = "find book by id")
    void getBookById(int id) {
        Book book = bookService.get(id);
        System.out.println(book);
    }

    @ShellMethod(value = "delete book")
    void deleteBook(int id) {
        bookService.delete(id);
    }

    @ShellMethod(value = "update description")
    void updateBook(int id, String description) {
        Book book = new Book();
        book.setId(id);
        book.setDescription(description);
        bookService.update(book);
    }

    @ShellMethod(value = "update tie")
    void updateBookTie(int id, int... authorId) {
        bookService.update(id, Arrays.stream(authorId).boxed().collect(Collectors.toList()));
    }

    @ShellMethod(value = "insert")
    void insertBook(String description, String genre) {
        Book book = new Book();
        book.setDescription(description);
        book.setGenre(Genre.valueOf(genre));
        bookService.insert(book, new ArrayList<>());
    }
}
