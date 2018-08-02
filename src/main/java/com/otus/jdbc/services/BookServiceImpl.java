package com.otus.jdbc.services;

import com.google.common.collect.Lists;
import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;
import com.otus.jdbc.repository.AuthorDataJpaRepository;
import com.otus.jdbc.repository.BookDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(rollbackOn = Throwable.class)
public class BookServiceImpl implements BookService {

    private final AuthorDataJpaRepository authorRepository;

    private final BookDataJpaRepository bookRepository;

    @Autowired
    public BookServiceImpl(AuthorDataJpaRepository authorRepository,
                           BookDataJpaRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getByAuthor(int id) {
        return authorRepository.findById(id).map(bookRepository::getAllByAuthor).orElse(Lists.newArrayList());
    }

    @Override
    public Book insert(Book book, List<Integer> authorIds) {
        book.setAuthor(getAuthors(authorIds));
        return bookRepository.save(book);
    }

    @Override
    public Book update(int idBook, List<Integer> authorIds) {
        Book reference = bookRepository.getOne(idBook);
        reference.setAuthor(getAuthors(authorIds));
        return bookRepository.save(reference);
    }

    @Override
    public Book update(Book book) {
        Book reference = bookRepository.getOne(book.getId());
        reference.setDescription(book.getDescription());
        reference.setGenre(book.getGenre());
        return bookRepository.save(reference);
    }

    private List<Author> getAuthors(List<Integer> authorIds) {
        return authorIds.stream().map(authorRepository::getOne).collect(toList());
    }

    @Override
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book get(int id) {
        return bookRepository.getOne(id);
    }
}
