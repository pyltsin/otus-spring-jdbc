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

    @ShellMethod(value = "create authors")
    void createAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        authorService.insert(author);
    }

    @ShellMethod(value = "find all authors")
    void getAllAuthors() {
        authorService.getAll().forEach(System.out::println);
    }


    @ShellMethod(value = "find authors by id")
    void getAuthorById(int id) {
        System.out.println(authorService.find(id));
    }

    @ShellMethod(value = "delete authors by id")
    void deleteAuthor(int id) {
        authorService.delete(id);
    }

    @ShellMethod(value = "update authors name")
    void updateAuthor(int id, String name) {
        Author author = new Author();
        author.setName(name);
        author.setId(id);
        authorService.update(author);
    }
}
