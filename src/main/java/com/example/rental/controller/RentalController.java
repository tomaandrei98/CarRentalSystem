package com.example.rental.controller;

import com.example.rental.dto.request.RequestRentalDto;
import com.example.rental.dto.request.RequestSaveRentalDto;
import com.example.rental.dto.response.ResponseRentalDto;
import com.example.rental.dto.response.general.ApiResponse;
import com.example.rental.dto.response.paginated.PaginatedResponseCustomerDto;
import com.example.rental.dto.response.paginated.PaginatedResponseRentalDto;
import com.example.rental.service.RentalService;
import com.example.rental.utils.logger.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    @Log
    public ResponseEntity<ApiResponse<List<ResponseRentalDto>>> getAllRentals() {
        return ResponseEntity.ok(
                new ApiResponse<>(rentalService.getAllRentals(), "Rentals downloaded successfully.")
        );
    }

    @GetMapping("/{rentalId}")
    @Log
    public ResponseEntity<ApiResponse<ResponseRentalDto>> getRentalById(@PathVariable("rentalId") Long rentalId) {

        return ResponseEntity.ok(
                new ApiResponse<>(rentalService.getRentalById(rentalId), "Rental downloaded successfully.")
        );
    }

    @GetMapping("/return/{rentalId}")
    @Log
    public ResponseEntity<ApiResponse<ResponseRentalDto>> returnRentalById(@PathVariable("rentalId") Long rentalId) {
        return ResponseEntity.ok(
                new ApiResponse<>(rentalService.returnRentalById(rentalId),
                "Rental returned successfully.")
        );
    }

    @GetMapping("/paginated")
    @Log
    public ResponseEntity<ApiResponse<PaginatedResponseRentalDto>> getRentalsPaginated(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(rentalService.getRentalsPaginated(pageNumber, pageSize, sortBy),
                        "Rentals downloaded successfully.")
        );
    }

    @PostMapping
    @Log
    public ResponseEntity<ApiResponse<ResponseRentalDto>> addRental(
            @RequestBody RequestSaveRentalDto requestSaveRentalDto
    ) {

        return ResponseEntity.status(CREATED)
                .body(new ApiResponse<>(rentalService.saveRental(requestSaveRentalDto),
                        "Rental created successfully."));
    }

    @PutMapping("/{rentalId}")
    @Log
    public ResponseEntity<ApiResponse<ResponseRentalDto>> updateRental(
            @PathVariable("rentalId") Long rentalId,
            @RequestBody RequestRentalDto requestRentalDto
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(rentalService.updateRental(rentalId, requestRentalDto),
                        "Rental updated successfully.")
        );
    }

    @DeleteMapping("/{rentalId}")
    @Log
    public ResponseEntity<ApiResponse<Void>> deleteRental(@PathVariable("rentalId") Long rentalId) {
        rentalService.deleteRentalById(rentalId);

        return ResponseEntity.ok(
                new ApiResponse<>("Rental deleted successfully.")
        );
    }
}
