package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.repository.AuthorDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDataJpaRepository authorRepository;
    private final NextSequenceService nextSequenceService;

    @Autowired
    public AuthorServiceImpl(AuthorDataJpaRepository authorRepository, NextSequenceService nextSequenceService) {
        this.authorRepository = authorRepository;
        this.nextSequenceService = nextSequenceService;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author get(int id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public Author insert(Author author) {
        if (author.getId() == 0) {
            author.setId(nextSequenceService.getNextSequence("author"));
        }
        return authorRepository.save(author);
    }

    @Override
    public Author update(Author author) {
        Author authorFromDb = get(author.getId());
        authorFromDb.setName(author.getName());
        return authorRepository.save(authorFromDb);
    }

    @Override
    public void delete(int id) {
        authorRepository.deleteById(id);
    }

    @Override
    public void save(Integer id, String name) {
        if (id != null) {
            Author author = new Author();
            author.setName(name);
            author.setId(id);
            update(author);
        } else {
            Author author = new Author();
            author.setName(name);
            insert(author);
        }
    }
}
