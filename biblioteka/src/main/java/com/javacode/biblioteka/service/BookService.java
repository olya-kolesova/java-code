package com.javacode.biblioteka.service;

import com.javacode.biblioteka.repository.BookRepositoryImpl;
import com.javacode.biblioteka.model.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepositoryImpl bookRepository;

    public BookService(BookRepositoryImpl bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book create(Book book) {
        Number id = bookRepository.insert(book);
        return bookRepository.findById(id.longValue());
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(long id) throws EmptyResultDataAccessException {
        return bookRepository.findById(id);
    }

    public Book update(long id, Book book) throws EmptyResultDataAccessException {
        Book foundBook = findById(id);
        foundBook.setAuthor(book.getAuthor());
        foundBook.setTitle(book.getTitle());
        foundBook.setPublicationYear(book.getPublicationYear());
        bookRepository.update(foundBook);
        return foundBook;
    }

    public void deleteById(long id) throws EmptyResultDataAccessException {
        findById(id);
        bookRepository.delete(id);
    }




}
