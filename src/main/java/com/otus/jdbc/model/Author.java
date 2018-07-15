package com.otus.jdbc.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Author implements Ids {
    private Long id;
    private String name;
}
