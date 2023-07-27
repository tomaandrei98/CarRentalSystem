package com.example.rental.enums;

public enum Status {
    AVAILABLE("available"),
    RENTED("rented"),
    MAINTENANCE("maintenance"),
    SOLD("sold");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
