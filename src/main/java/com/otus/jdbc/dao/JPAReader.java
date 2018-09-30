package com.otus.jdbc.dao;

import com.otus.jdbc.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface JPAReader extends CrudRepository<Author, Integer> {
}
