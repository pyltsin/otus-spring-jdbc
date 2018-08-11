package com.otus.jdbc.repository;

import com.otus.jdbc.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentDataJpaRepository extends MongoRepository<Comment, Integer> {
    List<Comment> getAllByBook(Integer book);
}
