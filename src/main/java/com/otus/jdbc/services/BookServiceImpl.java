package com.otus.jdbc.services;

import com.google.common.collect.Lists;
import com.otus.jdbc.model.Book;
import com.otus.jdbc.repository.AuthorDataJpaRepository;
import com.otus.jdbc.repository.BookDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorDataJpaRepository authorRepository;
    private final BookDataJpaRepository bookRepository;
    private final NextSequenceService nextSequenceService;

    @Autowired
    public BookServiceImpl(AuthorDataJpaRepository authorRepository,
                           BookDataJpaRepository bookRepository, NextSequenceService nextSequenceService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.nextSequenceService = nextSequenceService;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getByAuthor(int id) {
        return authorRepository.findById(id)
                .map(author -> bookRepository.getAllByAuthorsContains(author.getId()))
                .orElse(Lists.newArrayList());
    }

    @Override
    public Book insert(Book book, List<Integer> authorIds) {
        int id = nextSequenceService.getNextSequence("book");
        book.setId(id);
        book.setAuthors(authorIds);
        return bookRepository.save(book);
    }

    @Override
    public Book update(int idBook, List<Integer> authorIds) {
        Book reference = bookRepository.findById(idBook)
                .orElseThrow(() -> new IllegalArgumentException("book not found"));
        reference.setAuthors(authorIds);
        return bookRepository.save(reference);
    }

    @Override
    public Book update(Book book) {
        Book reference = bookRepository.findById(book.getId())
                .orElseThrow(() -> new IllegalArgumentException("book not found"));
        reference.setDescription(book.getDescription());
        reference.setGenre(book.getGenre());
        return bookRepository.save(reference);
    }

    @Override
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book get(int id) {
        return bookRepository.findById(id).orElse(null);
    }
}
