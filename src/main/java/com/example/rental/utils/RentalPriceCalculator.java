package com.example.rental.utils;

import com.example.rental.model.Rental;

import java.time.Period;

public class RentalPriceCalculator {
    public static Double calculatePriceForRental(Rental rental) {
        return rental.getCars().stream()
                .mapToDouble(car -> car.getRentalPricePerDay() *
                        Period.between(rental.getStartDate(), rental.getEndDate()).getDays()
                )
                .sum();
    }
}
