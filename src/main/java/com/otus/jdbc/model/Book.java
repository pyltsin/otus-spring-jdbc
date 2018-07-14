package com.otus.jdbc.model;

import lombok.Data;

import java.util.List;

@Data
public class Book implements Ids {
    private Long id;
    private String description;
    private String ISBN;
    private List<Long> authorId;
    private Genre genre;
}
