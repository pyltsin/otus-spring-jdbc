package com.otus.jdbc.controller;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @ShellMethod(value = "create author")
    void createAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        authorService.insert(author);
    }

    @ShellMethod(value = "get all authors")
    void getAllAuthors() {
        authorService.getAll().forEach(System.out::println);
    }


    @ShellMethod(value = "get author by id")
    void getAuthorById(int id) {
        System.out.println(authorService.get(id));
    }

    @ShellMethod(value = "delete author by id")
    void deleteAuthor(int id) {
        authorService.delete(id);
    }

    @ShellMethod(value = "update author name")
    void updateAuthor(int id, String name) {
        Author author = new Author();
        author.setName(name);
        author.setId(id);
        authorService.update(author);
    }

    @ShellMethod(value = "tran")
    void transact() throws Exception {
        authorService.testTransact();
    }
}
