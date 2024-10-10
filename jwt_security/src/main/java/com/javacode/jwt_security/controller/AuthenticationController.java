package com.javacode.jwt_security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javacode.jwt_security.dto.AuthenticationRequest;
import com.javacode.jwt_security.dto.RegisterRequest;
import com.javacode.jwt_security.dto.UnlockRequest;
import com.javacode.jwt_security.jsonview.Views;
import com.javacode.jwt_security.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @JsonView(Views.Public.class)
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(authenticationService.authenticate(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestParam("token") String token) {
        return new ResponseEntity<>(authenticationService.refresh(token), HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam("token") String token) {
        return new ResponseEntity<>(authenticationService.validateToken(token), HttpStatus.OK);
    }

//    @Secured({"ADMIN"})
    @JsonView(Views.Unlocked.class)
    @PutMapping("/unlock")
    public ResponseEntity<Object> unlock(UnlockRequest unlockRequest) {
        return new ResponseEntity<>(authenticationService.unlockUser(unlockRequest), HttpStatus.OK);
    }

}
