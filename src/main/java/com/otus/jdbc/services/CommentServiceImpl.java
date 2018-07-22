package com.otus.jdbc.services;

import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Comment;
import com.otus.jdbc.repository.BookRepository;
import com.otus.jdbc.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional(rollbackOn = Throwable.class)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Comment> getByBook(int id) {
        return commentRepository.getByBookId(id);
    }

    @Override
    public Comment insert(Comment comment, int bookId) {
        Book book = bookRepository.getReference(bookId);
        comment.setBook(book);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        Comment reference = commentRepository.getReference(comment.getId());
        reference.setComment(comment.getComment());
        return commentRepository.update(reference);
    }

    @Override
    public Comment update2(Comment comment) {
        Comment reference = commentRepository.getReference(comment.getId());
        reference.setComment(comment.getComment());
        return reference;
    }

    @Override
    public void delete(int id) {
        Comment reference = commentRepository.getReference(id);
        commentRepository.delete(reference);
    }
}
