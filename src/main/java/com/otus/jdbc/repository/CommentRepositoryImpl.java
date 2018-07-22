package com.otus.jdbc.repository;

import com.otus.jdbc.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends AbstractRepositoryImpl<Comment> implements CommentRepository {

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT a from Comment a", Comment.class);
        return query.getResultList();
    }

    @Override
    public Comment getById(int id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public Comment getReference(int id) {
        return entityManager.getReference(Comment.class, id);
    }

    @Override
    public List<Comment> getByBookId(int id) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "select comment from Comment  as comment join Book book on book = comment.book where book.id=:book_id", Comment.class);
        query.setParameter("book_id", id);
        return query.getResultList();
    }
}
