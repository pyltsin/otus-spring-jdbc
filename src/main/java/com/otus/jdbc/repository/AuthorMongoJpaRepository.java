package com.otus.jdbc.repository;

import com.otus.jdbc.model.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AuthorMongoJpaRepository extends ReactiveMongoRepository<Author, Integer> {
}
