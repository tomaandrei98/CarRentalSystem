package com.example.rental.controllers;

import com.example.rental.dto.request.RequestCustomerDto;
import com.example.rental.dto.response.ResponseCustomerDto;
import com.example.rental.dto.response.general.ApiResponse;
import com.example.rental.service.CustomerService;
import com.example.rental.utils.logger.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @Log
    public ResponseEntity<ApiResponse<List<ResponseCustomerDto>>> getAllCustomers() {
        return ResponseEntity.ok(
                new ApiResponse<>(customerService.getAllCustomers(), "Customers downloaded successfully.")
        );
    }

    @GetMapping("/{customerId}")
    @Log
    public ResponseEntity<ApiResponse<ResponseCustomerDto>> getCustomerById(
            @PathVariable("customerId") Long customerId
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(customerService.getCustomerById(customerId),
                        "Customer downloaded successfully.")
        );
    }

    @PostMapping
    @Log
    public ResponseEntity<ApiResponse<ResponseCustomerDto>> addCustomer(
            @RequestBody RequestCustomerDto requestCustomerDto
    ) {

        return ResponseEntity.status(CREATED)
                .body(new ApiResponse<>(customerService.saveCustomer(requestCustomerDto),
                        "Customer created successfully."));
    }

    @PutMapping("/{customerId}")
    @Log
    public ResponseEntity<ApiResponse<ResponseCustomerDto>> updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestBody RequestCustomerDto requestCustomerDto
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(customerService.updateCustomer(customerId, requestCustomerDto),
                        "Customer updated successfully.")
        );
    }

    @DeleteMapping("/{customerId}")
    @Log
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomerById(customerId);

        return ResponseEntity.ok(
                new ApiResponse<>("Customer deleted successfully.")
        );
    }
}