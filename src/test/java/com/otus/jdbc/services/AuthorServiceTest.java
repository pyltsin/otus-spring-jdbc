package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Objects.requireNonNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Test
    public void insertAndGetAll() {
        List<Author> before = authorService.getAll().collectList().block();

        Author tt1 = authorService.insert(new Author("tt1")).block();
        Author tt2 = authorService.insert(new Author("tt2")).block();

        List<Author> all = authorService.getAll().collectList().block();
        Assert.assertTrue(requireNonNull(all).contains(tt1));
        Assert.assertTrue(all.contains(tt2));
        Assert.assertEquals(2, all.size() - requireNonNull(before).size());
    }

    @Test
    public void insertGetUpdate() {
        String name = "vasya";
        Author vasya = authorService.insert(new Author(name)).block();
        Author author = authorService.get(requireNonNull(vasya).getId()).block();
        Assert.assertEquals(name, requireNonNull(author).getName());

        String nameUpdate = "fedya";
        author.setName(nameUpdate);
        Author afterUpdate = authorService.update(author).block();

        Assert.assertEquals(nameUpdate, afterUpdate.getName());
    }

    @Test
    public void delete() {
        Author vasya = authorService.insert(new Author("vasya4")).block();
        authorService.delete(vasya.getId()).block();
        List<Author> allAfterDelete = authorService.getAll().collectList().block();
        Assert.assertFalse(allAfterDelete.contains(vasya));
    }
}