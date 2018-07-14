package com.otus.jdbc.dao;

import com.google.common.collect.Iterables;
import com.otus.jdbc.model.Ids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
abstract public class AbstractDaoImpl<T extends Ids> implements AbstractDao<T> {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public AbstractDaoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public T findById(long id) {
        return Iterables.getFirst(jdbcOperations.query("select * from " + getTable() + " where id = ?", new Object[]{id}, getRowMapper()), null);
    }


    @Override
    public List<T> findAll() {
        return jdbcOperations.query("select * from " + getTable(), getRowMapper());
    }

    @Override
    public void delete(T object) {
        jdbcOperations.update("delete from " + getTable() + " where id = ?", object.getId());
    }

    protected abstract String getTable();

    protected abstract RowMapper<T> getRowMapper();
}
