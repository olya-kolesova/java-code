package com.javacode.jwt_security.service;

import com.javacode.jwt_security.dto.AuthenticationRequest;
import com.javacode.jwt_security.dto.AuthenticationResponse;
import com.javacode.jwt_security.dto.RegisterRequest;
import com.javacode.jwt_security.dto.UnlockRequest;
import com.javacode.jwt_security.model.Role;
import com.javacode.jwt_security.model.User;
import com.javacode.jwt_security.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
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

    private static final Logger logger = LogManager.getLogger(AuthenticationService.class);


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public boolean isAdminOccupied(String role) {
        return role.equalsIgnoreCase("admin") && userRepository.findByRole(Role.ADMIN).isPresent();
    }


    public User register(RegisterRequest registerRequest) throws IllegalArgumentException {
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
        return userRepository.save(user);

    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) throws BadCredentialsException,
            NoSuchElementException, LockedException {
        logger.info("User is trying to authenticate");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()));
        } catch (BadCredentialsException e) {
            User foundUser = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new NoSuchElementException("User not found"));
            if (!foundUser.isAccountNonLocked()) {
                throw new LockedException("Account is locked");
            }
            foundUser.setTrialsCounter(foundUser.getTrialsCounter() + 1);
            logger.info("Wrong password input. {} trials left", 5 - foundUser.getTrialsCounter());
            if (foundUser.getTrialsCounter() > 4 && !foundUser.getRole().equals(Role.ADMIN)) {
                foundUser.setNonlocked(false);
                logger.info("User is locked");
            }
                userRepository.save(foundUser);
                throw new BadCredentialsException("Wrong username or password");
        }

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
            () -> new BadCredentialsException("Wrong username or password"));
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateToken(new HashMap<>(), user);
        logger.info("User authenticated. Tokens generated.");
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

    public User unlockUser(UnlockRequest unlockRequest) throws NoSuchElementException {
        User user = userRepository.findByUsername(unlockRequest.username()).orElseThrow(NoSuchElementException::new);
        user.setNonlocked(true);
        user.setTrialsCounter(0);
        return userRepository.save(user);
    }

}
