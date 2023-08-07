package com.example.rental.dto.request;

import com.example.rental.enums.Status;
import com.example.rental.enums.Transmission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUpdateCarDto {
    private Long id;
    private String make;
    private String model;
    private Integer year;
    private String imageUrl;
    private Double rentalPricePerDay;
    private Status status;
    private Integer seats;
    private Transmission transmission;
    private Integer largeBag;
    private Integer smallBag;
    private Long categoryId;
}
