package com.otus.jdbc.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;

public class Book implements Ids {
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    private String description;
    private Genre genre;
}
