package com.javacode.oauthsocialapp.service;

import com.javacode.oauthsocialapp.model.AppUser;
import com.javacode.oauthsocialapp.model.CustomUserPrincipal;
import com.javacode.oauthsocialapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("CustomUserDetailsService is going to find saved user to login");
        AppUser appUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        logger.info("User has been found");
        return CustomUserPrincipal.create(appUser);
    }
}
