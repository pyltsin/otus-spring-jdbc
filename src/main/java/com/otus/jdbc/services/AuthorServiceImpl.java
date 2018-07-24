package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = Throwable.class)
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Override
    public List<Author> getByBook(int id) {
        return authorRepository.getByBookId(id);
    }

    @Override
    public Author get(int id) {
        return authorRepository.getById(id);
    }

    @Override
    public Author insert(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author update(Author author) {
        return authorRepository.update(author);
    }

    @Override
    public void delete(int id) {
        Author reference = authorRepository.getReference(id);
        authorRepository.delete(reference);
    }
}
