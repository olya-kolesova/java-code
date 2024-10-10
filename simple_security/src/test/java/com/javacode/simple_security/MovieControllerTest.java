package com.javacode.simple_security;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.simple_security.configuration.SecurityConfig;
import com.javacode.simple_security.controller.MovieController;
import com.javacode.simple_security.model.Movie;
import com.javacode.simple_security.service.MovieService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = MovieController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;


    @Autowired
    private ObjectMapper objectMapper;

    Movie movie;

    @BeforeEach
    public void setup() {
        movie = new Movie();
        movie.setTitle("The Shawshank Redemption");
        movie.setDirector("Frank Darabont");
        movie.setRating(9.3);
    }

    @Test
    @Order(1)
    public void index_withoutUser() throws Exception{
        ResultActions response = mockMvc.perform(get("/home"));
        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Maria", password = "12345", authorities = "ADMIN")
    @Order(2)
    public void saveMovie_givenMovieWhenAdmin_returnTheShawshankRedemption() throws Exception {
        given(movieService.save(any(Movie.class))).willReturn(movie);

        ResultActions response = mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(movie.getTitle())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.rating", is(movie.getRating())));
    }

    @Test
    @Order(3)
    @WithMockUser(value="Vladislav", username = "Vladislav", password = "12345", authorities = "USER")
    public void getMovielist_whenUser_retuenListTest() throws Exception{
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        given(movieService.findAll()).willReturn(movieList);

        ResultActions response = mockMvc.perform(get("/api/movie/list"));

        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(movieList.size())));

    }

}
