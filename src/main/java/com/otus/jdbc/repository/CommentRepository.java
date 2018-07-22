package com.otus.jdbc.repository;

import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Comment;

import java.util.List;

public interface CommentRepository extends AbstractRepository<Comment> {
    List<Comment> getByBookId(int id);
}
