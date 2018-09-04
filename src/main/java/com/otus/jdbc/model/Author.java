package com.otus.jdbc.model;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@ToString
@Document
public class Author {
    public Author() {
    }

    @Id
    private int id;

    private String name;

    public Author(String name) {
        this.name = name;
    }
}
