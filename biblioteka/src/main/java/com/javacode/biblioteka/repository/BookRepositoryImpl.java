package com.javacode.biblioteka.repository;

import com.javacode.biblioteka.BookRowMapper;
import com.javacode.biblioteka.model.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final NamedParameterJdbcTemplate template;

    private final BookRowMapper bookRowMapper;

    private final SimpleJdbcInsert insert;


    public BookRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
        this.bookRowMapper = new BookRowMapper();
        this.insert = new SimpleJdbcInsert(template.getJdbcTemplate());
        this.insert.setTableName("books");
        this.insert.usingGeneratedKeyColumns("id");

    }

    @Override
    public Number insert(Book book) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", book.getTitle());
        parameters.put("author", book.getAuthor());
        parameters.put("publication_year", book.getPublicationYear());
        return insert.executeAndReturnKey(parameters);
    }

    @Override
    public Book findById(long id) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM books WHERE id = :id";
        Map<String, Object> parameters = Collections.singletonMap("id", id);
        return template.queryForObject(sql, parameters, bookRowMapper);
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        return template.query(sql, bookRowMapper);
    }

    @Override
    public int update(Book book) {
        String sql = "UPDATE books SET title = :title, author = :author, publication_year = :publication_year WHERE id = :id";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", book.getId());
        parameters.put("title", book.getTitle());
        parameters.put("author", book.getAuthor());
        parameters.put("publication_year", book.getPublicationYear());
        return template.update(sql, parameters);
    }

    @Override
    public int delete(long id) {
        String sql = "DELETE FROM books WHERE id = :id";
        Map<String, Object> parameters = Collections.singletonMap("id", id);
        int rows = template.update(sql, parameters);
        System.out.println(rows);
        return rows;
    }


}
