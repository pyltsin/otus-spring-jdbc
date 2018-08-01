package com.otus.jdbc.repository;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDataJpaRepository extends JpaRepository<Book, Integer> {
    List<Book> getAllByAuthor(Author author);
}
