package com.otus.jdbc.repository;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDataJpaRepository extends JpaRepository<Comment, Integer> {
    List<Comment> getAllByBook(Book book);
}
