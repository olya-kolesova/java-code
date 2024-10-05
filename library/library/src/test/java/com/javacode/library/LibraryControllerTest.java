package com.javacode.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.library.model.Book;
import com.javacode.library.service.AuthorService;
import com.javacode.library.service.BookService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    Book book;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setTitle("Holiday in Prostokvashino");
    }

    @Test
    @Order(1)
    public void createBook_withBook_isCreated() throws Exception {
        given(bookService.save(any(Book.class))).willReturn(book);

        ResultActions response = mockMvc.perform(post("/api/library/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(book)));

        response.andExpect(status().isCreated())
            .andExpect(jsonPath("$.title", is(book.getTitle())));
    }

    @Test
    @Order(2)
    public void getBook_id_returnBook() throws Exception {
        given(bookService.findById(1)).willReturn(book);

        ResultActions response = mockMvc.perform(get("/api/library/book/{id}", 1));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.title", is(book.getTitle())));

    }

    @Test
    @Order(3)
    public void getSortedBooks_page0_returnPageable() throws Exception {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("title"));
        List<Book> books = new ArrayList<>();
        books.add(book);
        Book newBook = new Book();
        newBook.setTitle("Crocodile Gena and his friends");
        books.add(newBook);
        Page<Book> page = new PageImpl<>(books, pageable, books.size());
        given(bookService.findSortedByTitle(pageable)).willReturn(page);

        ResultActions response = mockMvc.perform(get("/api/library/sortedbooks?page=0"));

        response.andExpect(status().isOk())
            .andExpect(jsonPath("$['pageable']['paged']", is(true)));
    }

    @Test
    @Order(4)
    public void updateBook_withNewTitle_returnUserWithNewTitle() throws Exception {
        given(bookService.findById(1)).willReturn(book);
        book.setTitle("New Year in Prostokvashino");
        given(bookService.update(book.getId(), book)).willReturn(book);
        Book newTitleBook = new Book();
        newTitleBook.setTitle("New Year in Prostokvashino");

        ResultActions response = mockMvc.perform(put("/api/library/book/{id}", book.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newTitleBook)));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.title", is(book.getTitle())));
    }

    @Test
    @Order(5)
    public void deleteBook_withId_isOk() throws Exception {
        willDoNothing().given(bookService).remove(book.getId());

        ResultActions response = mockMvc.perform(delete("/api/library/book/{id}", book.getId()));

        response.andExpect(status().isOk());
    }



}
