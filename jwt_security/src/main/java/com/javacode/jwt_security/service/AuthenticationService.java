package com.javacode.jwt_security.service;

import com.javacode.jwt_security.dto.AuthenticationRequest;
import com.javacode.jwt_security.dto.AuthenticationResponse;
import com.javacode.jwt_security.dto.RegisterRequest;
import com.javacode.jwt_security.model.Role;
import com.javacode.jwt_security.model.User;
import com.javacode.jwt_security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public boolean isAdminOccupied(String role) {
        return role.equalsIgnoreCase("admin") && userRepository.findByRole(Role.ADMIN).isPresent();
    }


    public AuthenticationResponse register(RegisterRequest registerRequest) throws IllegalArgumentException {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        if (registerRequest.getRole() == null) {
            user.setRole(Role.USER);
        } else {
            if (isAdminOccupied(registerRequest.getRole())) {
                throw new IllegalArgumentException();
            }
            user.setRole(Role.valueOf(registerRequest.getRole().toUpperCase()));
        }
        userRepository.save(user);
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateToken(new HashMap<>(), user);
        return new AuthenticationResponse(accessToken, refreshToken);

    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws NoSuchElementException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(NoSuchElementException::new);
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateToken(new HashMap<>(), user);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse refresh(String refreshToken) throws NoSuchElementException {
        User user = userRepository.findByUsername(jwtService.extractUsername(refreshToken)).orElseThrow(NoSuchElementException::new);
        String accessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateToken(new HashMap<>(), user);
        return new AuthenticationResponse(accessToken, newRefreshToken);
    }

    public Boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }


}
