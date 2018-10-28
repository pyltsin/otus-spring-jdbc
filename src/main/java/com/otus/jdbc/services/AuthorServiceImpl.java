package com.otus.jdbc.services;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.otus.jdbc.model.Author;
import com.otus.jdbc.repository.AuthorDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDataJpaRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorDataJpaRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @HystrixCommand(fallbackMethod = "getAllFromArchive", groupKey = "get")
    public List<Author> getAll() {
        Random random = new Random();
        if (random.nextBoolean()) {
            return authorRepository.findAll();
        } else {
            throw new RuntimeException("не повезло");
        }
    }

    @Override
    public List<Author> getAllFromArchive() {
        Author author = new Author();
        author.setName("test");
        author.setId(1);
        return Lists.newArrayList(author);
    }


    @Override
    public Author get(int id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public Author insert(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author update(Author author) {
        Author authorFromDb = get(author.getId());
        authorFromDb.setName(author.getName());
        return authorRepository.save(authorFromDb);
    }

    @Override
    public void delete(int id) {
        authorRepository.deleteById(id);
    }

    @Override
    public void save(Integer id, String name) {
        if (id != null) {
            Author author = new Author();
            author.setName(name);
            author.setId(id);
            update(author);
        } else {
            Author author = new Author();
            author.setName(name);
            insert(author);
        }
    }
}
