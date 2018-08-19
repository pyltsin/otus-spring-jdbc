package com.otus.jdbc.repository;

import com.otus.jdbc.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDataJpaRepository extends JpaRepository<Author, Integer> {
}
