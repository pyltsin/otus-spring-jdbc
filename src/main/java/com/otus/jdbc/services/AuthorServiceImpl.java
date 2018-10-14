package com.otus.jdbc.services;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.repository.AuthorDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private MutableAclService aclService;

    private final AuthorDataJpaRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorDataJpaRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author get(int id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public Author insert(Author author) {
        final Author save = authorRepository.save(author);

        final ObjectIdentityImpl objectIdentity = new ObjectIdentityImpl(Author.class, save.getId());
        aclService.createAcl(objectIdentity);
        return save;
    }

    @Override
    public Author update(Author author) {
        Author authorFromDb = get(author.getId());
        authorFromDb.setName(author.getName());
        return authorRepository.save(authorFromDb);
    }

    @Override
    public void delete(int id) {
        final ObjectIdentityImpl objectIdentity = new ObjectIdentityImpl(Author.class, id);
        aclService.deleteAcl(objectIdentity, true);
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
