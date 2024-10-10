package com.javacode.jwt_security;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception) {
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception) {
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(LockedException.class)
//    public ResponseEntity<Object> handleLockedException(LockedException exception) {
//        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//    }


}
