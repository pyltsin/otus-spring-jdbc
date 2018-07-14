package com.otus.jdbc.dao;

import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Genre;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl extends AbstractDaoImpl<Book> implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public BookDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        super(jdbcOperations.getJdbcOperations());
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Book create(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(book);
        jdbcOperations.update("insert into book(isbn, genre, description) values(:isbn, :genre, :description)", parameters, keyHolder);
        book.setId(keyHolder.getKey().longValue());
        setAuthor(book);
        return book;
    }

    private void setAuthor(Book book) {
        jdbcOperations.getJdbcOperations().batchUpdate("insert into author_to_book(book_id, author_to_book) values (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, book.getId());
                ps.setLong(2, book.getAuthorId().get(i));
            }

            @Override
            public int getBatchSize() {
                return book.getAuthorId().size();
            }
        });
    }

    @Override
    public Book update(Book book) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", book.getId());
        map.put("isbn", book.getISBN());
        map.put("genre", book.getGenre().name());
        map.put("description", book.getAuthorId());
        jdbcOperations.update("update book set isbn=:isbn, genre=:genre, description=:description  where id=:id", map);

        deleteAuthor(book.getId());
        setAuthor(book);
        return book;
    }

    @Override
    public void delete(Book book) {
        super.delete(book);
        deleteAuthor(book.getId());
    }

    private void deleteAuthor(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("book_id", id);
        jdbcOperations.queryForList("delete from author_to_book where book_id = :book_id", map);
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
            book.setISBN(rs.getString("isbn"));
            book.setGenre(Genre.valueOf(rs.getString("genre")));
            book.setDescription(rs.getString("description"));
            book.setAuthorId(getAuthorId(book.getId()));
            return book;
        };
    }

    private List<Long> getAuthorId(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("book_id", id);
        return jdbcOperations.queryForList("select author_id from author_to_book where book_id = :book_id", map, Long.class);
    }

    @Override
    public List<Book> findByAuthor(long id) {
        return jdbcOperations.getJdbcOperations().query("select b.* from book b join author_to_book a on a.book_id=b.id where a.author_id=?", new Long[]{id}, getRowMapper());
    }
}
