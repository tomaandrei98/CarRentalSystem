package com.example.rental.exception;

import com.example.rental.exception.base.ResourceNotFoundException;

public class AppUserNotFoundException extends ResourceNotFoundException {
    public AppUserNotFoundException(String message) {
        super(message);
    }
}
