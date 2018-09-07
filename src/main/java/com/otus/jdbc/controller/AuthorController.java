package com.otus.jdbc.controller;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public Flux<Author> getAllAuthor() {
        log.debug("authors");
        return authorService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/authors")
    public Mono<Author> save(@RequestParam(value = "id", required = false) Integer id, @RequestParam("name") String name) {
        return authorService.save(id, name);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/authors")
    public Mono<Void> deleteAuthor(@RequestParam("id") Integer id) {
        return authorService.delete(id);
    }
}
