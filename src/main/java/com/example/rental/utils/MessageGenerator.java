package com.example.rental.utils;

public class MessageGenerator {

    public static String getCategoryNotFoundMessage(Long categoryId) {
        return String.format("category with id %s not found", categoryId);
    }

    public static String getCarNotFoundMessage(Long carId) {
        return String.format("car with is %d not found", carId);
    }

    public static String getCustomerNotFoundMessage(Long customerId) {
        return String.format("customer with id %s not found", customerId);
    }

    public static String getCustomerNotFoundMessage(String email) {
        return String.format("customer with email %s not found", email);
    }

    public static String getRentalNotFoundMessage(Long rentalId) {
        return String.format("rental with id %s not found", rentalId);
    }

    public static String getDeleteCategoryNotAcceptedMessage(Long categoryId) {
        return String.format("delete category with id %s not accepted. cars are associated with this category", categoryId);
    }

    public static String getDeleteCustomerNotAcceptedMessage(Long customerId) {
        return String.format("delete customer with id %s not accepted. rentals are associated with this customer", customerId);
    }

    public static String getDeleteCarNotAcceptedMessage(Long carId) {
        return String.format("delete car with id %s not accepted. rentals are associated with this car", carId);
    }

    public static String getSaveRentalNotAcceptedMessage() {
        return "you should choose one or more available cars to rent.";
    }

    public static String getEmailAlreadyTakenMessage(String email) {
        return String.format("email %s already taken.", email);
    }


}
