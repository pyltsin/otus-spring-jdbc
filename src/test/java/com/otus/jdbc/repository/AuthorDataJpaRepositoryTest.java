package com.otus.jdbc.repository;

import com.google.common.collect.Lists;
import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Genre;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDataJpaRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorDataJpaRepository authorRepository;

    @Test
    public void getAllByBooks() {
        Author author = new Author("Test");
        Book book = new Book("lala", Genre.HISTORY);
        book.setAuthor(Lists.newArrayList(author));
        entityManager.persist(author);
        entityManager.persist(book);

        entityManager.flush();

        List<Author> allByBooks = authorRepository.getAllByBooks(book);

        assertThat(allByBooks, Matchers.hasSize(1));
        assertThat(allByBooks, Matchers.hasItem(author));
    }
}
