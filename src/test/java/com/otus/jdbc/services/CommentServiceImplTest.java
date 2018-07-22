package com.otus.jdbc.services;

import com.otus.jdbc.model.Book;
import com.otus.jdbc.model.Comment;
import com.otus.jdbc.model.Genre;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.otus.jdbc.model.Genre.ROMAN;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@Transactional
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BookService bookService;

    private Book book;

    @Before
    public void setUp() throws Exception {
        book = bookService.insert(new Book("test", ROMAN), newArrayList());
    }

    @Test
    public void insertGetByBook() {
        Comment test = commentService.insert(new Comment("test"), book.getId());
        Comment test2 = commentService.insert(new Comment("test2"), book.getId());

        List<Comment> byBook = commentService.getByBook(book.getId());
        Assert.assertArrayEquals(new Comment[]{test, test2}, byBook.toArray());
    }

    @Test
    public void insertUpdate() {
        List<Comment> byBook = commentService.getByBook(book.getId());
        byBook.forEach(comment -> commentService.delete(comment.getId()));

        Comment test = commentService.insert(new Comment("test"), book.getId());
        String testComment = "tilitli";
        test.setComment(testComment);
        commentService.update(test);
        List<Comment> comments = commentService.getByBook(book.getId());
        Assert.assertEquals(1, comments.size());
        Assert.assertEquals(testComment, comments.get(0).getComment());

        String testComment2 = "test2ww";
        Comment comment = comments.get(0);
        comment.setComment(testComment2);
        commentService.update2(comment);
        comments = commentService.getByBook(book.getId());
        Assert.assertEquals(1, comments.size());
        Assert.assertEquals(testComment2, comments.get(0).getComment());

    }

    @Test
    public void delete() {
        List<Comment> all = commentService.getByBook(book.getId());
        Comment test = commentService.insert(new Comment("test"), book.getId());
        commentService.delete(test.getId());
        List<Comment> allAfterDelete = commentService.getByBook(book.getId());
        Assert.assertArrayEquals(all.toArray(), allAfterDelete.toArray());
    }
}