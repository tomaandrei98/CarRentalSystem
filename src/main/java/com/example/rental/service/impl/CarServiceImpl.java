package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestCarDto;
import com.example.rental.dto.request.RequestSaveCarDto;
import com.example.rental.dto.request.RequestUpdateCarDto;
import com.example.rental.dto.response.ResponseCarDto;
import com.example.rental.exception.CarNotFoundException;
import com.example.rental.exception.CategoryNotFoundException;
import com.example.rental.exception.DeleteCarNotAccepted;
import com.example.rental.model.Car;
import com.example.rental.model.Category;
import com.example.rental.repository.CarRepository;
import com.example.rental.repository.CategoryRepository;
import com.example.rental.service.CarService;
import com.example.rental.utils.converter.CarConverter;
import com.example.rental.utils.logger.Log;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.rental.utils.MessageGenerator.*;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final CarConverter carConverter;

    @Override
    @Log
    public List<ResponseCarDto> getAllCars() {
        return carRepository.findAll().stream()
                .map(carConverter::convertModelToResponseDto)
                .toList();
    }

    @Override
    @Log
    public ResponseCarDto getCarById(Long carId) {
        Car downloadedCar = carRepository.findById(carId)
                .orElseThrow(
                        () -> new CarNotFoundException(getCarNotFoundMessage(carId))
                );

        return carConverter.convertModelToResponseDto(downloadedCar);
    }

    @Override
    @Log
    public ResponseCarDto saveCar(RequestSaveCarDto requestSaveCarDto) {
        Long categoryId = requestSaveCarDto.getCategoryId();
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(getCategoryNotFoundMessage(categoryId)));
        Car carToSave = carConverter.convertRequestSaveToModel(requestSaveCarDto);

        carToSave.setCategory(savedCategory);
        savedCategory.addCar(carToSave);

        Car savedCar = carRepository.save(carToSave);
        return carConverter.convertModelToResponseDto(savedCar);
    }

    @Override
    @Log
    @Transactional
    public ResponseCarDto updateCar(RequestUpdateCarDto requestUpdateCarDto) {
        Long carDtoId = requestUpdateCarDto.getId();
        Optional<Car> optionalCar = carRepository.findById(carDtoId);

        optionalCar
                .orElseThrow(
                        () -> new CarNotFoundException(getCarNotFoundMessage(carDtoId))
                );

        optionalCar.ifPresent(updateCar -> {
            updateCar.setMake(requestUpdateCarDto.getMake());
            updateCar.setModel(requestUpdateCarDto.getModel());
            updateCar.setYear(requestUpdateCarDto.getYear());
            updateCar.setImageUrl(requestUpdateCarDto.getImageUrl());
            updateCar.setRentalPricePerDay(requestUpdateCarDto.getRentalPricePerDay());
            updateCar.setStatus(requestUpdateCarDto.getStatus());
        });

        return carConverter.convertModelToResponseDto(
                carRepository.findById(carDtoId)
                        .orElseThrow(
                                () -> new CarNotFoundException(getCarNotFoundMessage(carDtoId))
                        )
        );
    }

    @Override
    @Log
    public void deleteCarById(Long carId) {
        Car carToDelete = carRepository.findById(carId)
                .orElseThrow(
                        () -> new CarNotFoundException(getCarNotFoundMessage(carId))
                );

        if (!carToDelete.getRentals().isEmpty()) {
            throw new DeleteCarNotAccepted(getDeleteCarNotAcceptedMessage(carId));
        }

        carRepository.delete(carToDelete);
    }
}
