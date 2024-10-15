package com.javacode.oauthsocialapp.service;

import com.javacode.oauthsocialapp.model.AppUser;
import com.javacode.oauthsocialapp.model.CustomUserPrincipal;
import com.javacode.oauthsocialapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public OAuth2User save(OAuth2User oAuth2User) {
        String username = oAuth2User.getAttribute("login");

        if (username == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        AppUser appUser = userRepository.findByUsername(username).orElseGet(AppUser::new);
        if (appUser.getUsername() == null) {
            appUser.setUsername(username);
        }
        if (oAuth2User.getAttribute("email") != null) {
            appUser.setEmail(oAuth2User.getAttribute("email"));
        }

        if (oAuth2User.getAttribute("bio") != null) {
            appUser.setEmail(oAuth2User.getAttribute("bio"));
        }

        AppUser savedUser = userRepository.save(appUser);

        return CustomUserPrincipal.create(savedUser, oAuth2User.getAttributes());
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("Username not found"));
    }



}
