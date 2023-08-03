package com.example.rental.service;

import com.example.rental.dto.request.RequestSaveCarDto;
import com.example.rental.dto.request.RequestUpdateCarDto;
import com.example.rental.dto.response.ResponseCarDto;
import com.example.rental.model.Car;

import java.time.LocalDate;
import java.util.List;

public interface CarService {
    List<ResponseCarDto> getAllCars();

    List<ResponseCarDto> getAvailableCars(LocalDate startDate, LocalDate endDate);

    ResponseCarDto getCarById(Long carId);

    ResponseCarDto saveCar(RequestSaveCarDto requestSaveCarDto);

    ResponseCarDto updateCar(RequestUpdateCarDto requestUpdateCarDto);

    void deleteCarById(Long carId);

    List<ResponseCarDto> getAvailableCars();
}
