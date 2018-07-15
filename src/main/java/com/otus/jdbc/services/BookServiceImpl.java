package com.otus.jdbc.services;

import com.otus.jdbc.dao.AuthorToBookDao;
import com.otus.jdbc.dao.BookDao;
import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorToBookDao authorToBookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao, AuthorToBookDao authorToBookDao) {
        this.bookDao = bookDao;
        this.authorToBookDao = authorToBookDao;
    }


    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> getByAuthor(long id) {
        List<Long> bookByAuthor = authorToBookDao.getBookByAuthor(id);
        return bookByAuthor.stream().map(bookId -> bookDao.findById(bookId)).collect(toList());
    }

    @Override
    public Book insert(Book book) {
        return bookDao.insert(book);
    }

    @Override
    public Book update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public void insertToAutor(Book book, Author author) {
        authorToBookDao.insert(book.getId(), author.getId());
    }

    @Override
    public void delete(Book book) {
        authorToBookDao.deleteByBook(book.getId());
        bookDao.delete(book);
    }

    @Override
    public Book find(Long id) {
        return bookDao.findById(id);
    }
}
