package com.otus.jdbc.services;

import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Comment;
import com.otus.jdbc.repository.BookDataJpaRepository;
import com.otus.jdbc.repository.CommentDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional(rollbackOn = Throwable.class)
public class CommentServiceImpl implements CommentService {

    private final CommentDataJpaRepository commentRepository;

    private final BookDataJpaRepository bookRepository;

    @Autowired
    public CommentServiceImpl(CommentDataJpaRepository commentRepository,
                              BookDataJpaRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Comment> getByBook(int id) {
        return commentRepository.getAllByBook(bookRepository.getOne(id));
    }

    @Override
    public Comment insert(Comment comment, int bookId) {
        Book book = bookRepository.getOne(bookId);
        comment.setBook(book);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        Comment reference = commentRepository.getOne(comment.getId());
        reference.setComment(comment.getComment());
        return commentRepository.save(reference);
    }

    @Override
    public Comment update2(Comment comment) {
        Comment reference = commentRepository.getOne(comment.getId());
        reference.setComment(comment.getComment());
        return reference;
    }

    @Override
    public void delete(int id) {
        Comment reference = commentRepository.getOne(id);
        commentRepository.delete(reference);
    }
}
