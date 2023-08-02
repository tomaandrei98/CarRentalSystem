package com.example.rental.service;

import com.example.rental.dto.request.RequestCarDto;
import com.example.rental.dto.request.RequestSaveCarDto;
import com.example.rental.dto.request.RequestUpdateCarDto;
import com.example.rental.dto.response.ResponseCarDto;

import java.util.List;

public interface CarService {
    List<ResponseCarDto> getAllCars();

    ResponseCarDto getCarById(Long carId);

    ResponseCarDto saveCar(RequestSaveCarDto requestSaveCarDto);

    ResponseCarDto updateCar(RequestUpdateCarDto requestUpdateCarDto);

    void deleteCarById(Long carId);
}
