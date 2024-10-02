package com.javacode.internet_shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.internet_shop.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateUser() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User("Alice", "alice@gmail.com");
        String userJson = objectMapper.writeValueAsString(user);

        ResultActions result = mockMvc.perform(post("/api/shop/user")
            .contentType(MediaType.APPLICATION_JSON).content(userJson));

        result.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Alice"))
            .andExpect(jsonPath("$.email").value("alice@gmail.com"));

    }

}
