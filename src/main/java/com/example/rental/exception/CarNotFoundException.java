package com.example.rental.exception;

import com.example.rental.exception.base.ResourceNotFoundException;

public class CarNotFoundException extends ResourceNotFoundException {
    public CarNotFoundException(String message) {
        super(message);
    }
}
