package com.example.rental.exception;

import com.example.rental.exception.base.OperationNotAcceptedException;

public class DeleteCarNotAccepted extends OperationNotAcceptedException {
    public DeleteCarNotAccepted(String message) {
        super(message);
    }
}
