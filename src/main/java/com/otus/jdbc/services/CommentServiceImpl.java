package com.otus.jdbc.services;

import com.otus.jdbc.model.Comment;
import com.otus.jdbc.repository.CommentDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDataJpaRepository commentRepository;
    private final NextSequenceService nextSequenceService;

    @Autowired
    public CommentServiceImpl(CommentDataJpaRepository commentRepository, NextSequenceService nextSequenceService) {
        this.commentRepository = commentRepository;
        this.nextSequenceService = nextSequenceService;
    }

    @Override
    public List<Comment> getByBook(int id) {
        return commentRepository.getAllByBook(id);
    }

    @Override
    public Comment insert(Comment comment, int bookId) {
        int id = nextSequenceService.getNextSequence("comment");
        comment.setId(id);
        comment.setBook(bookId);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(int id) {
        commentRepository.deleteById(id);
    }
}
