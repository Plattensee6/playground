package com.example.urlshortener.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("Exception caught", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException caught", ex);
        List<String> fieldErrorMessages = ex.getFieldErrors().stream().flatMap(fe -> Stream.of(fe.getDefaultMessage())).collect(Collectors.toList());
        return new ResponseEntity<>(fieldErrorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleException(EntityNotFoundException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<String> handleException(MalformedURLException ex) {
        return new ResponseEntity<>("Invalid URL format!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<String> handleException(URISyntaxException ex) {
        return new ResponseEntity<>("Invalid URL format!", HttpStatus.BAD_REQUEST);
    }
}
