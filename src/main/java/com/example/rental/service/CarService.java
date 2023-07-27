package com.example.rental.service;

import com.example.rental.dto.request.RequestCarDto;
import com.example.rental.dto.response.ResponseCarDto;

import java.util.List;

public interface CarService {
    List<ResponseCarDto> getAllCars();

    ResponseCarDto getCarById(Long carId);

    ResponseCarDto saveCar(Long categoryId, RequestCarDto requestCarDto);

    ResponseCarDto updateCar(Long carId, RequestCarDto requestCarDto);

    void deleteCarById(Long carId);
}
