package com.example.rental.exception;

import com.example.rental.exception.base.OperationNotAcceptedException;

public class SaveRentalNotAccepted extends OperationNotAcceptedException {
    public SaveRentalNotAccepted(String message) {
        super(message);
    }
}
