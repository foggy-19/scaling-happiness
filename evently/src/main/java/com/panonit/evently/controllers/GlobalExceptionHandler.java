package com.panonit.evently.controllers;

import com.panonit.evently.domain.dtos.ErrorDto;
import com.panonit.evently.exceptions.BaseException;
import com.panonit.evently.exceptions.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestController
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        log.error("Caught Exception: ", ex);

        ErrorDto error = ErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred.")
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Caught ConstraintViolationException: ", ex);

        String message = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(violation ->
                        "Path: " + violation.getPropertyPath() + ", Message: " + violation.getMessage()
                )
                .orElse("Constraint violation occurred");

        ErrorDto error = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Caught MethodArgumentNotValidException: ", ex);

        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(error ->
                        "Field: " + error.getField() + ", Message: " + error.getDefaultMessage()
                )
                .orElse("Validation error occurred");

        ErrorDto error = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDto> handleBaseException(BaseException ex) {
        log.error("Caught BaseException: ", ex);

        ErrorDto error = ErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected base error occurred.")
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("Caught UserNotFoundException: ", ex);

        ErrorDto error = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("User not found.")
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
