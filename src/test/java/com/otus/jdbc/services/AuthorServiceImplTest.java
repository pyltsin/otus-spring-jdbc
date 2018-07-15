package com.otus.jdbc.services;

import com.otus.jdbc.JdbcApplication;
import com.otus.jdbc.model.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorServiceImplTest {

    @Autowired
    AuthorService authorService;

    @Test
    public void findAll() {
        List<Author> all = authorService.findAll();
        assertEquals(all.size(), 3);
    }

    @Test
    public void getByBook() {
        List<Author> authors = authorService.getByBook(1);
        assertEquals(authors.size(), 2);
    }

    @Test
    public void insert() {
        Author author = new Author();
        author.setName("LL");
        Author insert = authorService.insert(author);
        assertNotNull(insert.getId());
        Author authorFromBD = authorService.find(insert.getId());
        assertEquals(insert.getName(), authorFromBD.getName());
    }

    @Test
    public void update() {
        Author author = authorService.find(1);
        author.setName("LL");
        authorService.update(author);
        Author authorFromBd = authorService.find(1);
        assertEquals(author.getName(), authorFromBd.getName());
    }

    @Test
    public void delete() {
        Author author = authorService.find(1);
        authorService.delete(author);
        List<Author> all = authorService.findAll();
        assertEquals(all.size(), 2);
    }
}