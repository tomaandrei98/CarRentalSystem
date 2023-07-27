package com.example.rental.exception;

import com.example.rental.exception.base.ResourceNotFoundException;

public class RentalNotFoundException extends ResourceNotFoundException {
    public RentalNotFoundException(String message) {
        super(message);
    }
}
