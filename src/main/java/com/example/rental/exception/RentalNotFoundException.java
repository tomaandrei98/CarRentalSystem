package com.example.rental.exception;

public class RentalNotFoundException extends ResourceNotFoundException {
    public RentalNotFoundException(String message) {
        super(message);
    }
}
