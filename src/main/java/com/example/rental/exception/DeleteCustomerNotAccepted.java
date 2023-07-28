package com.example.rental.exception;

import com.example.rental.exception.base.OperationNotAcceptedException;

public class DeleteCustomerNotAccepted extends OperationNotAcceptedException {
    public DeleteCustomerNotAccepted(String message) {
        super(message);
    }
}
