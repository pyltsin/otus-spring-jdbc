package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.repository.AuthorMongoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMongoJpaRepository authorRepository;
    private final NextSequenceService nextSequenceService;

    @Autowired
    public AuthorServiceImpl(AuthorMongoJpaRepository authorRepository, NextSequenceService nextSequenceService) {
        this.authorRepository = authorRepository;
        this.nextSequenceService = nextSequenceService;
    }

    @Override
    public Flux<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> get(int id) {
        return authorRepository.findById(id);
    }

    @Override
    public Mono<Author> insert(Author author) {
        if (author.getId() == 0) {
            author.setId(nextSequenceService.getNextSequence("author"));
        }
        return authorRepository.save(author);
    }

    @Override
    public Mono<Author> update(Author author) {
        Mono<Author> authorFromDb = get(author.getId());
        return authorFromDb.doOnNext(_a -> _a.setName(author.getName())).doOnNext(authorRepository::save);
    }

    @Override
    public Mono<Void> delete(int id) {
        return authorRepository.deleteById(id);
    }

    @Override
    public Mono<Author> save(Integer id, String name) {
        if (id != null) {
            Author author = new Author();
            author.setName(name);
            author.setId(id);
            return update(author);
        } else {
            Author author = new Author();
            author.setName(name);
            return insert(author);
        }
    }
}
