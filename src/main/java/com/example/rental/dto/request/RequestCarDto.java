package com.example.rental.dto.request;

import com.example.rental.dto.base.BaseDto;
import com.example.rental.enums.Status;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class RequestCarDto extends BaseDto {
    private String make;
    private String model;
    private Integer year;
    private String imageUrl;
    private Double rentalPricePerDay;
    private Status status;
}
