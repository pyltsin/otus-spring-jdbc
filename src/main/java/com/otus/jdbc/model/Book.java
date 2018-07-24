package com.otus.jdbc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ToString
@EqualsAndHashCode(of = "id")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AUTHOR_TO_BOOK", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> author;

    @OneToMany(mappedBy = "book", cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    private List<Comment> comments;

    public Book(String description, Genre genre) {
        this.description = description;
        this.genre = genre;
    }

    public Book() {

    }
}
