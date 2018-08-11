package com.otus.jdbc.controller;

import com.otus.jdbc.model.Comment;
import com.otus.jdbc.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ShellMethod(value = "find all comment")
    void getAllComment(int id) {
        commentService.getByBook(id).forEach(System.out::println);
    }

    @ShellMethod(value = "delete comment")
    void deleteComment(int id) {
        commentService.delete(id);
    }


    @ShellMethod(value = "insert comment")
    void insertComment(int id, String text) {
        Comment comment = new Comment();
        comment.setComment(text);
        commentService.insert(comment, id);
    }

    @ShellMethod(value = "updateComment1")
    void updateComment1(int id, String text) {
        Comment comment = new Comment();
        comment.setComment(text);
        comment.setId(id);
        commentService.update(comment);
    }
}
