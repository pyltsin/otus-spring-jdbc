package com.otus.jdbc.services;

import com.otus.jdbc.JdbcApplication;
import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookServiceImplTest {

    @Autowired
    BookService bookService;

    @Test
    public void findAll() {
        List<Book> all = bookService.findAll();
        assertEquals(all.size(), 3);
    }

    @Test
    public void getByBook() {
        List<Book> authors = bookService.getByAuthor(1);
        assertEquals(authors.size(), 1);
    }

    @Test
    public void insert() {
        Book book = new Book();
        book.setDescription("MM");
        book.setGenre(Genre.FANTAZY);
        Book insert = bookService.insert(book);
        assertNotNull(insert.getId());
        Book authorFromBD = bookService.find(insert.getId());
        assertEquals(insert.getGenre(), authorFromBD.getGenre());
        assertEquals(insert.getDescription(), authorFromBD.getDescription());
    }

    @Test
    public void update() {
        Book book = bookService.find(1L);
        book.setDescription("LL");
        bookService.update(book);
        Book fromBd = bookService.find(1L);
        assertEquals(book.getDescription(), fromBd.getDescription());
    }

    @Test
    public void delete() {
        Book book = bookService.find(1L);
        bookService.delete(book);
        List<Book> all = bookService.findAll();
        assertEquals(all.size(), 2);
    }
}