package com.otus.jdbc.model;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Getter
@ToString
@Document
@Entity
@Table(name = "author")
public class Author {
    public Author() {
    }

    @Id
    @Transient
    private ObjectId _id;

    @javax.persistence.Id
    private int id;
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
