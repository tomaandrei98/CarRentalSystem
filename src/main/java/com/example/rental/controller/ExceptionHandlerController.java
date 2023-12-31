package com.example.rental.controller;

import com.example.rental.dto.response.general.ApiResponse;
import com.example.rental.exception.EmailAlreadyTakenException;
import com.example.rental.exception.base.OperationNotAcceptedException;
import com.example.rental.exception.base.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handlerResourceNotFoundException(ResourceNotFoundException exception) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("error message", exception.getLocalizedMessage());

        return new ResponseEntity<>(new ApiResponse<>(responseBody, exception.getLocalizedMessage()), NOT_FOUND);
    }

    @ExceptionHandler(OperationNotAcceptedException.class)
    public ResponseEntity<ApiResponse<Object>> handlerOperationNotAcceptedException(OperationNotAcceptedException exception) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("error message", exception.getLocalizedMessage());

        return new ResponseEntity<>(new ApiResponse<>(responseBody, exception.getLocalizedMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<ApiResponse<Object>> handlerEmailAlreadyTakenException(EmailAlreadyTakenException exception) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("error message", exception.getLocalizedMessage());

        return new ResponseEntity<>(new ApiResponse<>(responseBody, exception.getLocalizedMessage()), CONFLICT);
    }
}
