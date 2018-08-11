package com.otus.jdbc.repository;

import com.otus.jdbc.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthorDataJpaRepository extends MongoRepository<Author, Integer> {
    List<Author> getAllByBooksContains(Integer book);
}
