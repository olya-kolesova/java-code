package com.javacode.oauthsocialapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        logger.error("IllegalArgumentException, details: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        logger.error("UsernameNotFoundException, details: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<String> handleOauth2AuthenticationException(OAuth2AuthenticationException exception) {
        logger.error("OAuth2AuthenticationException, details: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
