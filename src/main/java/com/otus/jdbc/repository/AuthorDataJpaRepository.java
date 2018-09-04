package com.otus.jdbc.repository;

import com.otus.jdbc.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorDataJpaRepository extends MongoRepository<Author, Integer> {
}
