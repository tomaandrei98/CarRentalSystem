package com.example.rental.exception.base;

public class OperationNotAcceptedException extends RuntimeException {
    public OperationNotAcceptedException(String message) {
        super(message);
    }
}
