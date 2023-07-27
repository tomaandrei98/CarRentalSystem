package com.example.rental.exception;

public class CarNotFoundException extends ResourceNotFoundException {
    public CarNotFoundException(String message) {
        super(message);
    }
}
