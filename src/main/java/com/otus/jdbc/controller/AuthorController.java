package com.otus.jdbc.controller;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(method = RequestMethod.GET, path = "/authors")
    public List<Author> getAllAuthor() {
        log.debug("authors");
        return authorService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/authors")
    public ResponseEntity save(@RequestParam(value = "id", required = false) Integer id, @RequestParam("name") String name) {
        authorService.save(id, name);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/authors")
    public ResponseEntity deleteAuthor(@RequestParam("id") Integer id) {
        authorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
