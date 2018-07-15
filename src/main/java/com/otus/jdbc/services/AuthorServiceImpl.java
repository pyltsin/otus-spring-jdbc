package com.otus.jdbc.services;

import com.otus.jdbc.dao.AuthorDao;
import com.otus.jdbc.dao.AuthorToBookDao;
import com.otus.jdbc.dao.BookDao;
import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    private final AuthorToBookDao authorToBookDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, AuthorToBookDao authorToBookDao) {
        this.authorDao = authorDao;
        this.authorToBookDao = authorToBookDao;
    }


    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    @Override
    public List<Author> getByBook(long id) {
        List<Long> bookByAuthor = authorToBookDao.getAuthorByBook(id);
        return bookByAuthor.stream().map(authorDao::findById).collect(toList());
    }

    @Override
    public Author find(long id) {
        return authorDao.findById(id);
    }

    @Override
    public Author insert(Author author) {
        return authorDao.insert(author);
    }

    @Override
    public Author update(Author author) {
        return authorDao.update(author);
    }

    @Override
    public void delete(Author author) {
        authorToBookDao.deleteByAuthor(author.getId());
        authorDao.delete(author);
    }
}
