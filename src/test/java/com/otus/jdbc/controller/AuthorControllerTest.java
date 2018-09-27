package com.otus.jdbc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.jdbc.model.Author;
import com.otus.jdbc.repository.AuthorDataJpaRepository;
import com.otus.jdbc.services.AuthorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Pyltsin on 25.08.2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorDataJpaRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user1")
    public void getAll() throws Exception {
        List<Author> authors = authorRepository.findAllById(Arrays.asList(2, 3));

        mockMvc.perform(get("/authors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(authors)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteAuthor() throws Exception {

        Author author = new Author("Pelevin");
        Author authorFromDB = authorService.insert(author);

        mockMvc.perform(delete("/authors").param("id", String.valueOf(authorFromDB.getId())))
                .andDo(print())
                .andExpect(status().isOk());

        Assert.assertFalse(authorRepository.findAll().contains(authorFromDB));
    }


    @Test
    @WithMockUser(roles = "USER")
    public void updateAuthor() throws Exception {

        Author author = new Author("Pelevin");
        Author authorFromDB = authorService.insert(author);

        String nameAfterUpdate = "Pelevin2";
        authorFromDB.setName(nameAfterUpdate);
        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(authorFromDB.getId()))
                .param("name", authorFromDB.getName()))
                .andDo(print())
                .andExpect(status().isOk());

        Assert.assertEquals(authorService.get(authorFromDB.getId()).getName(), nameAfterUpdate);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void createAuthor() throws Exception {

        List<Author> allBeforeInsert = authorRepository.findAll();
        String testName = "TEST";
        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", testName))
                .andDo(print())
                .andExpect(status().isOk());

        List<Author> allAfterInsert = authorRepository.findAll();

        allAfterInsert.removeAll(allBeforeInsert);

        Assert.assertEquals(allAfterInsert.size(), 1);
        Assert.assertEquals(allAfterInsert.get(0).getName(), testName);
    }
}
