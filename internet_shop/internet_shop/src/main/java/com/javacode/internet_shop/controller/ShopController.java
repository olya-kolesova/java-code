package com.javacode.internet_shop.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javacode.internet_shop.jview.Views;
import com.javacode.internet_shop.model.User;
import com.javacode.internet_shop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopController {

    private final UserService userService;

    ShopController(UserService userService) {
        this.userService = userService;
    }

    @JsonView(Views.UserSummary.class)
    @PostMapping("api/shop/user")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if (userService.isUserPresent(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            User returnedUser = userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @JsonView(Views.UserSummary.class)
    @GetMapping("/api/shop/user/list")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @JsonView(Views.UserDetails.class)
    @GetMapping("/api/shop/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }




}
