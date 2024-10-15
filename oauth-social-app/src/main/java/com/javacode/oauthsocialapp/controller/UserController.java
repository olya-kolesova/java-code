package com.javacode.oauthsocialapp.controller;

import com.javacode.oauthsocialapp.model.AppUser;
import com.javacode.oauthsocialapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;


@Controller
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public ResponseEntity<String> login() {
        logger.info("Visiting public page.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String message = authentication.getName();
        return ResponseEntity.ok("Hello from Public endpoint - " + message);
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal, Model model) {
        logger.info("Visiting user page.");
        String username = principal.getAttribute("login");
        AppUser user = userService.findByUsername(username);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("bio", user.getBio());
        return "user";
    }

    @GetMapping("/admin")
    public String admin(Principal principal, Model model) {
        logger.info("Visiting admin page.");
        AppUser admin = userService.findByUsername(principal.getName());
        model.addAttribute("username", principal.getName());
        model.addAttribute("bio", admin.getBio());
        return "admin";
    }

}
