package com.javacode.library.controller;

import com.javacode.library.model.Book;
import com.javacode.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LibraryController {

    private final BookService bookService;

    public LibraryController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("api/library/sortedbooks")
    public ResponseEntity<Page<Book>> getBooksSortedByTitle(@RequestParam("page") int page) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by("title"));
        return new ResponseEntity<>(bookService.findSortedByTitle(pageable), HttpStatus.OK) ;
    }

    @PostMapping("/api/library/book")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/api/library/book/{id}")
    public ResponseEntity<Object> updateInformation(@PathVariable long id, @RequestBody Book book) {
        return new ResponseEntity<>(bookService.update(id, book), HttpStatus.OK);
    }

    @GetMapping("/api/library/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
    }


    @DeleteMapping("api/library/book/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable long id) {
        bookService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
