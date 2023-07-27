package com.example.rental.utils.converter;

import com.example.rental.dto.request.RequestCarDto;
import com.example.rental.dto.response.ResponseCarDto;
import com.example.rental.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarConverter {
    public ResponseCarDto convertModelToResponseDto(Car car) {
        ResponseCarDto response = ResponseCarDto.builder()
                .make(car.getMake())
                .model(car.getModel())
                .year(car.getYear())
                .imageUrl(car.getImageUrl())
                .rentalPricePerDay(car.getRentalPricePerDay())
                .status(car.getStatus())
                .build();
        response.setId(car.getId());
        return response;
    }

    public Car convertRequestToModel(RequestCarDto requestCarDto) {
        return Car.builder()
                .make(requestCarDto.getMake())
                .model(requestCarDto.getModel())
                .year(requestCarDto.getYear())
                .imageUrl(requestCarDto.getImageUrl())
                .rentalPricePerDay(requestCarDto.getRentalPricePerDay())
                .status(requestCarDto.getStatus())
                .build();
    }
}