package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestCarDto;
import com.example.rental.dto.response.ResponseCarDto;
import com.example.rental.exception.CarNotFoundException;
import com.example.rental.model.Car;
import com.example.rental.repository.CarRepository;
import com.example.rental.service.CarService;
import com.example.rental.utils.converter.CarConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.rental.utils.MessageGenerator.getCarNotFoundMessage;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarConverter carConverter;

    @Override
    public List<ResponseCarDto> getAllCars() {
        return carRepository.findAll().stream()
                .map(carConverter::convertModelToResponseDto)
                .toList();
    }

    @Override
    public ResponseCarDto getCarById(Long carId) {
        Car downloadedCar = carRepository.findById(carId)
                .orElseThrow(
                        () -> new CarNotFoundException(getCarNotFoundMessage(carId))
                );

        return carConverter.convertModelToResponseDto(downloadedCar);
    }

    @Override
    public ResponseCarDto saveCar(RequestCarDto requestCarDto) {
        Car carToSave = carConverter.convertRequestToModel(requestCarDto);
        Car savedCar = carRepository.save(carToSave);
        return carConverter.convertModelToResponseDto(savedCar);
    }

    @Override
    @Transactional
    public ResponseCarDto updateCar(Long carId, RequestCarDto requestCarDto) {
        Optional<Car> optionalCar = carRepository.findById(carId);

        optionalCar
                .orElseThrow(
                        () -> new CarNotFoundException(getCarNotFoundMessage(carId))
                );

        optionalCar.ifPresent(updateCar -> {
            updateCar.setMake(requestCarDto.getMake());
            updateCar.setModel(requestCarDto.getModel());
            updateCar.setYear(requestCarDto.getYear());
            updateCar.setImageUrl(requestCarDto.getImageUrl());
            updateCar.setRentalPricePerDay(requestCarDto.getRentalPricePerDay());
            updateCar.setStatus(requestCarDto.getStatus());
        });

        return carConverter.convertModelToResponseDto(
                carRepository.findById(carId)
                        .orElseThrow(
                                () -> new CarNotFoundException(getCarNotFoundMessage(carId))
                        )
        );
    }

    @Override
    public void deleteCarById(Long carId) {
        Car carToDelete = carRepository.findById(carId)
                .orElseThrow(
                        () -> new CarNotFoundException(getCarNotFoundMessage(carId))
                );

        carRepository.delete(carToDelete);
    }
}
