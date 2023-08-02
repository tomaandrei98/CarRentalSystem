package com.example.rental.dto.request;

import com.example.rental.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestSaveCarDto {
    private String make;
    private String model;
    private Integer year;
    private String imageUrl;
    private Double rentalPricePerDay;
    private Status status;
    private Long categoryId;
}
