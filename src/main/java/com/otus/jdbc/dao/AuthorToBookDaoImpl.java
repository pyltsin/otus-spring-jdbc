package com.otus.jdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorToBookDaoImpl implements AuthorToBookDao {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public AuthorToBookDaoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void insert(long idBook, long idAuthor) {
        jdbcOperations.update("insert into author_to_book(book_id, author_id) values (?, ?)", idBook, idAuthor);
    }

    @Override
    public void deleteByBook(long idBook) {
        jdbcOperations.update("delete from author_to_book where book_id=?", idBook);
    }

    @Override
    public void deleteByAuthor(long idAuthor) {
        jdbcOperations.update("delete from author_to_book where author_id=?", idAuthor);
    }

    @Override
    public List<Long> getBookByAuthor(long id) {
        return jdbcOperations.queryForList("select book_id from author_to_book where author_id = ?", Long.class, id);
    }

    @Override
    public List<Long> getAuthorByBook(long id) {
        return jdbcOperations.queryForList("select author_id from author_to_book where book_id = ?", Long.class, id);
    }
}
