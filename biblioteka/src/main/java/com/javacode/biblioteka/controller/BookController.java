package com.javacode.biblioteka.controller;

import com.javacode.biblioteka.model.Book;
import com.javacode.biblioteka.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping("/api/biblioteka/book")
    public ResponseEntity<Object> create(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.create(book), HttpStatus.CREATED);
    }

    @GetMapping("/api/biblioteka/book/list")
    public ResponseEntity<List<Book>> getBook() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/biblioteka/book/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/api/biblioteka/book/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody Book book) {
        return new ResponseEntity<>(bookService.update(id, book), HttpStatus.OK);
    }


    @DeleteMapping("/api/biblioteka/book/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
