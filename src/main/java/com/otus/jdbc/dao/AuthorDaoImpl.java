package com.otus.jdbc.dao;

import com.otus.jdbc.model.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthorDaoImpl extends AbstractDaoImpl<Author> implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        super(jdbcOperations.getJdbcOperations());
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Author insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(author);
        jdbcOperations.update("insert into author(name) values(:name)", parameters, keyHolder);
        author.setId(keyHolder.getKey().longValue());
        return author;
    }

    @Override
    public Author update(Author author) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", author.getId());
        map.put("name", author.getName());
        jdbcOperations.update("update author set name=:name where id=:id", map);
        return author;
    }

    @Override
    protected String getTable() {
        return "author";
    }

    @Override
    protected RowMapper<Author> getRowMapper() {
        return (rs, rowNum) -> {
            Author author = new Author();
            author.setId(rs.getLong("id"));
            author.setName(rs.getString("name"));
            return author;
        };
    }
}
