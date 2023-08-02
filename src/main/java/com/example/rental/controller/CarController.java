package com.example.rental.controller;

import com.example.rental.dto.request.RequestCarDto;
import com.example.rental.dto.request.RequestSaveCarDto;
import com.example.rental.dto.request.RequestUpdateCarDto;
import com.example.rental.dto.response.ResponseCarDto;
import com.example.rental.dto.response.general.ApiResponse;
import com.example.rental.service.CarService;
import com.example.rental.utils.logger.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CarController {

    private final CarService carService;

    @GetMapping
    @Log
    public ResponseEntity<ApiResponse<List<ResponseCarDto>>> getAllCars() {
        return ResponseEntity.ok(
                new ApiResponse<>(carService.getAllCars(), "Cars downloaded successfully.")
        );
    }

    @GetMapping("/{carId}")
    @Log
    public ResponseEntity<ApiResponse<ResponseCarDto>> getCarById(@PathVariable("carId") Long carId) {

        return ResponseEntity.ok(
                new ApiResponse<>(carService.getCarById(carId), "Car downloaded successfully.")
        );
    }

    @PostMapping
    @Log
    public ResponseEntity<ApiResponse<ResponseCarDto>> saveCar(
            @RequestBody RequestSaveCarDto requestSaveCarDto) {

        return ResponseEntity.status(CREATED)
                .body(new ApiResponse<>(carService.saveCar(requestSaveCarDto),
                        "Car created successfully.")
                );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ResponseCarDto>> updateCar(
            @RequestBody RequestUpdateCarDto requestUpdateCarDto
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(carService.updateCar(requestUpdateCarDto), "Car updated successfully.")
        );
    }

    @DeleteMapping("/{carId}")
    @Log
    public ResponseEntity<ApiResponse<Void>> deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCarById(carId);

        return ResponseEntity.ok(
                new ApiResponse<>("Car deleted successfully.")
        );
    }
}
