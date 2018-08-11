package com.otus.jdbc.repository;

import com.otus.jdbc.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookDataJpaRepository extends MongoRepository<Book, Integer> {
    List<Book> getAllByAuthorsContains(Integer author);
}
