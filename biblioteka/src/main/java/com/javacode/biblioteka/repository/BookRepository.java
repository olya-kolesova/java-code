package com.javacode.biblioteka.repository;

import com.javacode.biblioteka.model.Book;

import java.util.List;

public interface BookRepository {
    Number insert(Book book);

    Book findById(long id);

    List<Book> findAll();

    int update(Book book);

    int delete(long id);

}
