package com.otus.jdbc.repository;

import com.otus.jdbc.model.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookRepositoryImpl extends AbstractRepositoryImpl<Book> implements BookRepository {

    @Override
    public void delete(Book entity) {
        entityManager.createQuery("delete from Comment comments where comments.book.id = :id").setParameter("id", entity.getId()).executeUpdate();
        entityManager.createQuery("delete from Book book where book.id = :id").setParameter("id", entity.getId()).executeUpdate();
//        super.delete(entity);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT distinct a from Book a left join fetch a.author", Book.class);
        return query.getResultList();
    }

    @Override
    public Book getById(int id) {
//        return entityManager.find(Book.class, id);
        TypedQuery<Book> query = entityManager.createQuery(
                "select distinct book from Book as book left join fetch book.author where book.id=:book_id", Book.class);
        query.setParameter("book_id", id);
        Book singleResult = query.getSingleResult();
        System.out.println("contains:" +entityManager.contains(singleResult));
        System.out.println(entityManager.getFlushMode());
        return singleResult;
    }

    @Override
    public Book getReference(int id) {
        return entityManager.getReference(Book.class, id);
    }

    @Override
    public List<Book> getByAuthorId(int id) {
        TypedQuery<Book> query = entityManager.createQuery(
                "select distinct book from Book as book left join fetch book.author author  where author.id=:author_id", Book.class);
        query.setParameter("author_id", id);
        return query.getResultList();
    }
}
