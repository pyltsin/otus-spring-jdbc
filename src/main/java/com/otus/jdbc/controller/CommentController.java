package com.otus.jdbc.controller;

import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Comment;
import com.otus.jdbc.model.Genre;
import com.otus.jdbc.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@ShellComponent
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ShellMethod(value = "get all comment")
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

    @ShellMethod(value = "updateComment2")
    void updateComment2(int id, String text) {
        Comment comment = new Comment();
        comment.setComment(text);
        comment.setId(id);
        commentService.update2(comment);
    }
}
