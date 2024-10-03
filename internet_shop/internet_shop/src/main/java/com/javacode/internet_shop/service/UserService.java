package com.javacode.internet_shop.service;

import com.javacode.internet_shop.model.User;
import com.javacode.internet_shop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean isUserPresent(String email) {
        try {
            userRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    public User save(User user) throws IllegalArgumentException {
        if (isEmailValid(user.getEmail()) && !(user.getName().isBlank())) {
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    public User findUserById(long id) throws NoSuchElementException {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public boolean isEmailValid(String email) {
        return Pattern.compile("\\w+.*\\w*@[a-zA-Z]+\\.[a-zA-Z]+").matcher(email).matches();
    }


    @Transactional
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }




}
