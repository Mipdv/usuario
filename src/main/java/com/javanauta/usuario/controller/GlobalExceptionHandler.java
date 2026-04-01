package com.javanauta.usuario.controller;

import com.javanauta.usuario.infrastructure.exceptions.ConflictExcpection;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.exceptions.UnathorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictExcpection.class)
    public ResponseEntity<String> handleConflictException(ConflictExcpection exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnathorizedException.class)
    public ResponseEntity<String> handleUnathorizedException(UnathorizedException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
