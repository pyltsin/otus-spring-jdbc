package com.otus.jdbc.services;

import com.otus.jdbc.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getByBook(int id);

    Comment insert(Comment comment, int bookId);

    Comment update(Comment comment);

    void delete(int  id);
}
