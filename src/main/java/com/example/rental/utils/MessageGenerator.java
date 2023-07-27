package com.example.rental.utils;

public class MessageGenerator {

    public static String getCategoryNotFoundMessage(Long categoryId) {
        return String.format("category with is %s not found", categoryId);
    }

    public static String getCarNotFoundMessage(Long carId) {
        return String.format("car with is %s not found", carId);
    }

    public static String getCustomerNotFoundMessage(Long customerId) {
        return String.format("customer with is %s not found", customerId);
    }

    public static String getRentalNotFoundMessage(Long rentalId) {
        return String.format("rental with is %s not found", rentalId);
    }
}
