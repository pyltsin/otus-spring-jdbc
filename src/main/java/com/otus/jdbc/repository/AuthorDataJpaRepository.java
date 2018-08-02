package com.otus.jdbc.repository;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorDataJpaRepository extends JpaRepository<Author, Integer> {
    List<Author> getAllByBooks(Book book);
}
