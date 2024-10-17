package com.javacode.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleJsonProcessingException(Exception ex, WebRequest request) {
        logger.error("JsonProcessingException occurred");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
