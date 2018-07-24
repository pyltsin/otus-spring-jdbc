package com.otus.jdbc.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Getter
@Entity
@ToString
public class Author {
    public Author() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public Author(String name) {
        this.name = name;
    }

    @ToString.Exclude
    @ManyToMany(mappedBy = "author")
    private List<Book> books;
}
