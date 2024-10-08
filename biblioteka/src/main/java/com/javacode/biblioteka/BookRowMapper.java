package com.javacode.biblioteka;

import com.javacode.biblioteka.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int roNum) throws SQLException {
        long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        int publicationYear = resultSet.getInt("publication_year");
        return new Book(id, title, author, publicationYear);
    }
}
