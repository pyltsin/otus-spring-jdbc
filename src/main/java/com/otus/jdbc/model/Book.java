package com.otus.jdbc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@ToString
@EqualsAndHashCode(of = "id")
public class Book {

    @Id
    private int id;

    private String description;

    private Genre genre;

    private List<Integer> authors;

    public Book(String description, Genre genre) {
        this.description = description;
        this.genre = genre;
    }

    public Book() {

    }
}
