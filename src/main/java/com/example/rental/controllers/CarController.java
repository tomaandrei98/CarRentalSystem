package com.example.rental.controllers;

import com.example.rental.dto.request.RequestCarDto;
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
    public ResponseEntity<ApiResponse<ResponseCarDto>> saveCar(@RequestBody RequestCarDto requestCarDto) {

        return ResponseEntity.status(CREATED)
                .body(new ApiResponse<>(carService.saveCar(requestCarDto), "Car created successfully."));
    }

    @PutMapping("{carId}")
    public ResponseEntity<ApiResponse<ResponseCarDto>> updateCar(
            @PathVariable("carId") Long carId,
            @RequestBody RequestCarDto requestCarDto
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(carService.updateCar(carId, requestCarDto), "Car updated successfully.")
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
