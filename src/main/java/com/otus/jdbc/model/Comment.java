package com.otus.jdbc.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@ToString
public class Comment {

    public Comment(String comment) {
        this.comment = comment;
    }

    public Comment() {
    }

    @Id
    private int id;

    private Integer book;

    private String comment;
}
