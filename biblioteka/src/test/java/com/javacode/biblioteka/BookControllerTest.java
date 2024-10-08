package com.javacode.biblioteka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.biblioteka.controller.BookController;
import com.javacode.biblioteka.model.Book;
import com.javacode.biblioteka.service.BookService;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    Book book;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Three comrades");
        book.setAuthor("E.M.Remark");
        book.setPublicationYear(1932);
    }


    @Test
    @Order(1)
    public void saveBook_givenbook_isCreated() throws Exception{
        given(bookService.create(any(Book.class))).willReturn(book);

        ResultActions response = mockMvc.perform(post("/api/biblioteka/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.author", is(book.getAuthor())))
                .andExpect(jsonPath("$.publicationYear", is(book.getPublicationYear())));
    }

    @Test
    @Order(2)
    public void getBookList_returnList() throws Exception {
        List<Book> bookList = new ArrayList<>();
        Book anotherBook = new Book();
        anotherBook.setId(2L);
        anotherBook.setTitle("Arch of Triumph");
        anotherBook.setAuthor("E.M.Remark");
        anotherBook.setPublicationYear(1935);
        bookList.add(book);


        bookList.add(book);
        bookList.add(anotherBook);
        given(bookService.findAll()).willReturn(bookList);

        ResultActions response = mockMvc.perform(get("/api/biblioteka/book/list"));

        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(bookList.size())));

    }

    @Test
    @Order(3)
    public void findById_withId_returnThreeComrades() throws Exception{

        given(bookService.findById(book.getId())).willReturn(book);


        ResultActions response = mockMvc.perform(get("/api/biblioteka/book/{id}", book.getId()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.author", is(book.getAuthor())))
                .andExpect(jsonPath("$.publicationYear", is(book.getPublicationYear())));

    }

    @Test
    @Order(4)
    public void updateBook_withGivenData_returnUpdated() throws Exception{

        given(bookService.findById(book.getId())).willReturn(book);

        book.setTitle("The Black Obelisk");
        book.setPublicationYear(1959);
        given(bookService.update(book.getId(), book)).willReturn(book);


        ResultActions response = mockMvc.perform(put("/api/biblioteka/book/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is(book.getTitle())))
            .andExpect(jsonPath("$.publicationYear", is(book.getPublicationYear())));
    }


    @Test
    public void deleteById_withId1_isOk() throws Exception{

        willDoNothing().given(bookService).deleteById(book.getId());

        ResultActions response = mockMvc.perform(delete("/api/biblioteka/book/{id}", book.getId()));

        response.andExpect(status().isOk());
    }
}


