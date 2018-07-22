package com.otus.jdbc.services;

import com.otus.jdbc.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getByBook(int id);

    Comment insert(Comment comment, int bookId);

    Comment update(Comment comment);

    //проверял работать ли менеджмент
    Comment update2(Comment comment);

    void delete(int  id);
}
