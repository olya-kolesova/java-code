package com.javacode.oauthsocialapp.model;

import com.javacode.oauthsocialapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

//@Component
public class UserDbInitializer {
    private final UserRepository userRepository;

    public UserDbInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @PostConstruct
    private void createTemporaryUser() {
        AppUser admin = new AppUser("admin", "{noop}12345", "admin@gmail.com", "java developer",
        "ADMIN");
        userRepository.save(admin);
    }
}
