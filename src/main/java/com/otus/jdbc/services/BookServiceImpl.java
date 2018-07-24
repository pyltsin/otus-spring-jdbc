package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;
import com.otus.jdbc.repository.AuthorRepository;
import com.otus.jdbc.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(rollbackOn = Throwable.class)
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public List<Book> getByAuthor(int id) {
        return bookRepository.getByAuthorId(id);
    }

    @Override
    public Book insert(Book book, List<Integer> authorIds) {
        book.setAuthor(getAuthors(authorIds));
        return bookRepository.save(book);
    }

    @Override
    public Book update(int idBook, List<Integer> authorIds) {
        Book reference = bookRepository.getReference(idBook);
        reference.setAuthor(getAuthors(authorIds));
        return bookRepository.save(reference);
    }

    @Override
    public Book update(Book book) {
        Book reference = bookRepository.getReference(book.getId());
        reference.setDescription(book.getDescription());
        reference.setGenre(book.getGenre());
        return bookRepository.update(reference);
    }

    private List<Author> getAuthors(List<Integer> authorIds) {
        return authorIds.stream().map(authorRepository::getReference).collect(toList());
    }

    @Override
    public void delete(int id) {
        Book reference = bookRepository.getReference(id);
        bookRepository.delete(reference);
    }

    @Override
    public Book get(int id) {
        return bookRepository.getById(id);
    }
}
