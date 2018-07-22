package com.otus.jdbc.repository;

import com.otus.jdbc.model.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorRepositoryImpl extends AbstractRepositoryImpl<Author> implements AuthorRepository {

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = entityManager.createQuery("SELECT a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Author getById(int id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public Author getReference(int id) {
        return entityManager.getReference(Author.class, id);
    }

    @Override
    public List<Author> getByBookId(int id) {
        TypedQuery<Author> query = entityManager.createQuery(
                "select author from Author  as author join author.books  book where book.id=:book_id", Author.class);
        query.setParameter("book_id", id);
        return query.getResultList();
    }
}
