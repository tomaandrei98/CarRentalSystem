package com.example.rental.dto.response;

import com.example.rental.dto.base.BaseDto;
import com.example.rental.enums.Status;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ResponseCarDto extends BaseDto {
    private String make;
    private String model;
    private Integer year;
    private String imageUrl;
    private Double rentalPricePerDay;
    private Status status;
}
