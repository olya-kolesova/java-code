package com.javacode.library.service;

import com.javacode.library.model.Author;
import com.javacode.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> findAllById(Set<Long> authorIds) {
        return authorRepository.findAllById(authorIds);
    }

    public Author findById(Long id) throws NoSuchElementException {
        return authorRepository.findById(id).orElseThrow();
    }

}
