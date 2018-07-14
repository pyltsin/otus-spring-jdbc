package com.otus.jdbc.model;

import lombok.Data;

@Data
public class Author implements Ids {
    private Long id;
    private String name;
}
