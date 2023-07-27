package com.example.rental.exception;

import com.example.rental.exception.base.OperationNotAcceptedException;

public class DeleteCategoryNotAccepted extends OperationNotAcceptedException {
    public DeleteCategoryNotAccepted(String message) {
        super(message);
    }
}
