package com.example.rental.controller;

import com.example.rental.dto.request.RequestSaveCarDto;
import com.example.rental.dto.request.RequestUpdateCarDto;
import com.example.rental.dto.response.ResponseCarDto;
import com.example.rental.dto.response.general.ApiResponse;
import com.example.rental.dto.response.paginated.PaginatedResponseCarDto;
import com.example.rental.service.CarService;
import com.example.rental.utils.logger.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/available")
    @Log
    public ResponseEntity<ApiResponse<List<ResponseCarDto>>> getAvailableCars(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(carService.getAvailableCars(startDate, endDate),
                        "Available cars downloaded successfully.")
        );
    }

    @GetMapping("/{carId}")
    @Log
    public ResponseEntity<ApiResponse<ResponseCarDto>> getCarById(@PathVariable("carId") Long carId) {

        return ResponseEntity.ok(
                new ApiResponse<>(carService.getCarById(carId), "Car downloaded successfully.")
        );
    }

    @GetMapping("/filter")
    @Log
    public ResponseEntity<ApiResponse<List<ResponseCarDto>>> getCarsByMatchingName(
            @RequestParam("matchingName") String matchingName) {

        return ResponseEntity.ok(
            new ApiResponse<>(carService.getCarsByMatchingName(matchingName),
                    "Cars downloaded successfully.")
        );
    }

    @GetMapping("/paginated")
    @Log
    public ResponseEntity<ApiResponse<PaginatedResponseCarDto>> getCarsPaginated(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "make") String sortBy
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(carService.getCarsPaginated(pageNumber, pageSize, sortBy),
                        "Cars downloaded successfully.")
        );
    }

    @GetMapping("/category/{categoryId}")
    @Log
    public ResponseEntity<ApiResponse<List<ResponseCarDto>>> getCarByCategoryId(
            @PathVariable("categoryId") Long categoryId
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(carService.getCarByCategoryId(categoryId), "Cars downloaded successfully.")
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

    @GetMapping("/populate")
    @Log
    public ResponseEntity<ApiResponse<Void>> populateWithCars() {
        carService.populateWithCars();

        return ResponseEntity.ok(
                new ApiResponse<>("Cars added successfully")
        );
    }
}
