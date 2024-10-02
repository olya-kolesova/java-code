package com.javacode.internet_shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.internet_shop.model.User;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String userJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User("Alice", "alice@gmail.com");
        userJson = objectMapper.writeValueAsString(user);
    }

    @Test
    public void createUser_existedUser_Conflict() throws Exception {

        ResultActions result = mockMvc.perform(post("/api/shop/user")
                .contentType(MediaType.APPLICATION_JSON).content(userJson));

        result.andExpect(status().isConflict());

    }

    @Test
    public void getUsers_ReturnAll() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/shop/user/list"));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.[*]").exists());

    }


    @Test
    public void getUser_withId1_returnUser() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/shop/user/{id}", 1));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Olya"))
                .andExpect(jsonPath("$.email").value("kolesik_svoya@mail.ru"));
    }



}
