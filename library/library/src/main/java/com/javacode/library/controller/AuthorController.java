package com.javacode.library.controller;

import com.javacode.library.model.Author;
import com.javacode.library.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/api/library/author")
    public ResponseEntity<Object> createAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(authorService.save(author), HttpStatus.CREATED);
    }

}
