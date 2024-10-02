package com.javacode.internet_shop.controller;

import com.javacode.internet_shop.model.User;
import com.javacode.internet_shop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopController {

    private final UserService userService;

    ShopController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/shop/user")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if (userService.isUserPresent(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            User returnedUser = userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @GetMapping("/api/shop/user/list")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }




}
