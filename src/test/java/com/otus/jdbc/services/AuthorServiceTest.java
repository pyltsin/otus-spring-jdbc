package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Test
    public void insertAndGetAll() {
        List<Author> before = authorService.getAll();

        Author tt1 = authorService.insert(new Author("tt1"));
        Author tt2 = authorService.insert(new Author("tt2"));

        List<Author> all = authorService.getAll();
        Assert.assertTrue(all.contains(tt1));
        Assert.assertTrue(all.contains(tt2));
        Assert.assertEquals(2, all.size() - before.size());
    }

    @Test
    public void insertGetUpdate() {
        String name = "vasya";
        Author vasya = authorService.insert(new Author(name));
        Author author = authorService.get(vasya.getId());
        Assert.assertEquals(name, author.getName());

        String nameUpdate = "fedya";
        author.setName(nameUpdate);
        authorService.update(author);

        author = authorService.get(vasya.getId());
        Assert.assertEquals(nameUpdate, author.getName());
    }

    @Test
    public void delete() {
        List<Author> all = authorService.getAll();
        Author vasya = authorService.insert(new Author("test"));
        authorService.delete(vasya.getId());
        List<Author> allAfterDelete = authorService.getAll();
        Assert.assertArrayEquals(all.toArray(), allAfterDelete.toArray());
    }
}