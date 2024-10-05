package com.javacode.library.service;

import com.javacode.library.model.Author;
import com.javacode.library.model.Book;
import com.javacode.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class BookService {


    private final AuthorService authorService;
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public Page<Book> findSortedByTitle(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public List<Author> checkAuthors(List<Author> authors) throws NoSuchElementException {
        Set<Long> authorIds = authors.stream().map(Author::getId).collect(Collectors.toSet());
        return authorService.findAllById(authorIds).stream().toList();
    }

    public Book save(Book book) {
        List<Author> authors = checkAuthors(book.getAuthors());
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    @Transactional
    public Book findById(long id) throws NoSuchElementException {
        return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Book update(long id, Book book) throws NoSuchElementException {
        Book foundBook = findById(id);
        foundBook.setTitle(book.getTitle());
        foundBook.setAuthors(book.getAuthors());
        return bookRepository.save(foundBook);
    }

    @Transactional
    public void remove(long id) throws NoSuchElementException {
        Book foundBook = findById(id);
        bookRepository.deleteById(id);
    }



}
