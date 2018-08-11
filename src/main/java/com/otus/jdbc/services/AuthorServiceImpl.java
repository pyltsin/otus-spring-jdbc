package com.otus.jdbc.services;

import com.google.common.collect.Lists;
import com.otus.jdbc.model.Author;
import com.otus.jdbc.repository.AuthorDataJpaRepository;
import com.otus.jdbc.repository.BookDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDataJpaRepository authorRepository;
    private final BookDataJpaRepository bookDataJpaRepository;
    private final NextSequenceService nextSequenceService;

    @Autowired
    public AuthorServiceImpl(AuthorDataJpaRepository authorRepository, BookDataJpaRepository bookDataJpaRepository, NextSequenceService nextSequenceService) {
        this.authorRepository = authorRepository;
        this.bookDataJpaRepository = bookDataJpaRepository;
        this.nextSequenceService = nextSequenceService;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> getByBook(int id) {
        return bookDataJpaRepository.findById(id).map(book -> authorRepository.getAllByBooksContains(book.getId())).orElse(Lists.newArrayList());
    }

    @Override
    public Author find(int id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author insert(Author author) {
        int id = nextSequenceService.getNextSequence("author");
        author.setId(id);
        return authorRepository.save(author);
    }

    @Override
    public Author update(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void delete(int id) {
        authorRepository.deleteById(id);
    }
}
