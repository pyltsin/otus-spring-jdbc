package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {

    Flux<Author> getAll();

    Mono<Author> get(int id);

    Mono<Author> insert(Author author);

    Mono<Author> update(Author author);

    Mono<Void> delete(int id);

    Mono<Author> save(Integer id, String name);
}
