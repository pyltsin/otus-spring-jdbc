package com.otus.jdbc.dao;

import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class BookDaoImpl extends AbstractDaoImpl<Book> implements BookDao {

    private final JdbcTemplate jdbcOperations;

    public BookDaoImpl(JdbcTemplate jdbcOperations) {
        super(jdbcOperations);
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Book insert(Book book) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcOperations);
        simpleJdbcInsert.setTableName(getTable());
        simpleJdbcInsert.setGeneratedKeyName("id");
        Map<String, String> map = new HashMap<>();
        map.put("description", book.getDescription());
        map.put("genre", book.getGenre().name());
        Integer id = (Integer) simpleJdbcInsert.executeAndReturnKey(map);
        book.setId(Long.valueOf(id));
        return book;
    }

    @Override
    public Book update(Book book) {
        jdbcOperations.update("update book set " +
                "genre=?," +
                "description=? where id=?", ps -> {
                    ps.setString(1, book.getGenre().name());
                    ps.setString(2, book.getDescription());
                    ps.setLong(3, book.getId());
                });

        return book;
    }

    @Override
    protected String getTable() {
        return "book";
    }

    @Override
    protected RowMapper<Book> getRowMapper() {
        return (rs, rowNum) -> {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setGenre(Genre.valueOf(rs.getString("genre")));
            book.setDescription(rs.getString("description"));
            return book;
        };
    }
}
