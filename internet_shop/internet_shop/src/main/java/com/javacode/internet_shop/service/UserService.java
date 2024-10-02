package com.javacode.internet_shop.service;

import com.javacode.internet_shop.model.User;
import com.javacode.internet_shop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findUserById(long id) throws NoSuchElementException {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }


}
