package com.otus.jdbc.services;

import com.google.common.collect.Lists;
import com.otus.jdbc.model.Author;
import com.otus.jdbc.repository.AuthorDataJpaRepository;
import com.otus.jdbc.repository.BookDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDataJpaRepository authorRepository;
    private final BookDataJpaRepository bookDataJpaRepository;

    @Autowired
    public AuthorServiceImpl(AuthorDataJpaRepository authorRepository, BookDataJpaRepository bookDataJpaRepository) {
        this.authorRepository = authorRepository;
        this.bookDataJpaRepository = bookDataJpaRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> getByBook(int id) {
        return bookDataJpaRepository.findById(id).map(authorRepository::getAllByBooks).orElse(Lists.newArrayList());
    }

    @Override
    public Author get(int id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public Author insert(Author author) {
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

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED)
    public void testTransact() throws Exception {
        authorRepository.save(new Author("test_transact"));
        inner();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void inner() throws Exception {
        authorRepository.save(new Author("test_transact2"));
        throw new Exception();
    }
}
