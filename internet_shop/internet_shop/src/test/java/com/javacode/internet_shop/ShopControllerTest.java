package com.javacode.internet_shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.internet_shop.controller.ShopController;
import com.javacode.internet_shop.model.User;
import com.javacode.internet_shop.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    User user;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        user = new User();
        user.setName("Kolya");
        user.setEmail("kolya@gmail.com");
    }

    @Test
    @Order(1)
    public void createUser_newUser_isCreated() throws Exception {

        given(userService.save(any(User.class))).willReturn(user);

        ResultActions response = mockMvc.perform(post("/api/shop/user")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)));

        response.andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(user.getName()))
            .andExpect(jsonPath("$.email").value(user.getEmail()));

    }
//
    @Test
    @Order(2)
    public void getUsers_Return2() throws Exception {

        List<User> userList = new ArrayList<>();
        User anotherUser = new User();
        anotherUser.setName("Andrey");
        anotherUser.setEmail("andrey@gmail.com");
        userList.add(user);
        userList.add(anotherUser);

        given(userService.getUsers()).willReturn(userList);

        ResultActions response = mockMvc.perform(get("/api/shop/user/list"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(userList.size())));

    }

    @Test
    @Order(3)
    public void getUser_withId1_returnUserWithOrders() throws Exception {

        given(userService.findUserById(1)).willReturn(user);

        ResultActions response = mockMvc.perform(get("/api/shop/user/{id}", 1));

       response.andExpect(status().isOk())
           .andExpect(jsonPath("$.name", is(user.getName())))
            .andExpect(jsonPath("$.email", is(user.getEmail())))
            .andExpect(jsonPath("$.orders").exists());
    }

    @Test
    @Order(4)
    public void updateUser_setNikolay_returnNikolay() throws Exception {

        given(userService.findUserById(1)).willReturn(user);
        user.setName("Nikolay");
        user.setEmail("nikolay@gmail.com");
        given(userService.save(user)).willReturn(user);

        ResultActions result = mockMvc.perform(put("/api/shop/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)));

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(user.getName())))
            .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    @Order(5)
    public void deleteUser_id_isOk() throws Exception {

        willDoNothing().given(userService).removeUser(1);

        ResultActions response = mockMvc.perform(delete("/api/shop/user/{id}", 1));

        response.andExpect(status().isOk());
    }





}
