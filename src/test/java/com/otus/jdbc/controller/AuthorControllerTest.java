package com.otus.jdbc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.jdbc.model.Author;
import com.otus.jdbc.services.AuthorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Created by Pyltsin on 25.08.2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerTest {

    @Autowired
    ApplicationContext context;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAll() throws Exception {
        List<Author> authors = authorService.getAll().collectList().block();
        webTestClient.get().uri("/authors")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(objectMapper.writeValueAsString(authors));
    }

    @Test
    public void deleteAuthor() {

        Author author = new Author("Pelevin");
        Author authorFromDB = authorService.insert(author).block();

        webTestClient
                .delete()
                .uri(
                        uriBuilder -> uriBuilder.path("/authors")
                                .queryParam("id", authorFromDB.getId())
                                .build())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk();

        Assert.assertFalse(requireNonNull(authorService.getAll().collectList().block()).contains(authorFromDB));
    }


    @Test
    public void createAuthor() throws Exception {

        List<Author> allBeforeInsert = authorService.getAll().collectList().block();
        String testName = "TEST";
        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/authors").queryParam("name", testName).build())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk();

        List<Author> allAfterInsert = authorService.getAll().collectList().block();

        requireNonNull(allAfterInsert).removeAll(requireNonNull(allBeforeInsert));

        Assert.assertEquals(allAfterInsert.size(), 1);
        Assert.assertEquals(allAfterInsert.get(0).getName(), testName);
    }

}
