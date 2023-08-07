package com.example.rental.enums;

public enum Transmission {
    MANUAL("manual"),
    AUTOMATIC("automatic");

    private final String transmission;

    Transmission(String transmission) {
        this.transmission = transmission;
    }

    public String getTransmission() {
        return transmission;
    }
}
