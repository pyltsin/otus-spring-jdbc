package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Genre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Transactional
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Test
    public void insertAndGetAll() {

        List<Book> before = bookService.getAll();

        Book tt1 = bookService.insert(new Book("tt1", Genre.ROMAN), newArrayList());
        Book tt2 = bookService.insert(new Book("tt2", Genre.HISTORY), newArrayList());

        List<Book> all = bookService.getAll();
        Assert.assertTrue(all.contains(tt1));
        Assert.assertTrue(all.contains(tt2));
        Assert.assertEquals(2, all.size() - before.size());
    }

    @Test
    public void insertGetUpdate() {
        String description = "vasya";
        Book vasya = bookService.insert(new Book(description, Genre.ROMAN), newArrayList());
        Book book = bookService.get(vasya.getId());
        Assert.assertEquals(description, book.getDescription());

        String nameUpdate = "fedya";
        book.setDescription(nameUpdate);
        Genre history = Genre.HISTORY;
        book.setGenre(history);
        bookService.update(book);

        book = bookService.get(vasya.getId());
        Assert.assertEquals(nameUpdate, book.getDescription());
        Assert.assertEquals(history, book.getGenre());
    }

    @Test
    public void getByAuthorAndUpdateLink() {
        Author tolstoy = authorService.insert(new Author("Tolstoy"));
        Author tolstoy2 = authorService.insert(new Author("Tolstoy2"));
        Book warAndPiece = bookService.insert(new Book("War and piece", Genre.ROMAN), newArrayList(tolstoy.getId()));

        List<Book> byAuthor = bookService.getByAuthor(tolstoy.getId());

        Assert.assertEquals(1, byAuthor.size());
        Assert.assertEquals(warAndPiece, byAuthor.get(0));

        bookService.update(warAndPiece.getId(), newArrayList(tolstoy2.getId()));
        Book book = bookService.get(warAndPiece.getId());
        Assert.assertEquals(1, book.getAuthor().size());
        Assert.assertEquals(book.getAuthor().get(0), tolstoy2);


    }

    @Test
    public void delete() {
        List<Book> all = bookService.getAll();
        Book test = bookService.insert(new Book("test", Genre.ROMAN), newArrayList());
        bookService.delete(test.getId());
        List<Book> allAfterDelete = bookService.getAll();
        Assert.assertArrayEquals(all.toArray(), allAfterDelete.toArray());
    }
}
