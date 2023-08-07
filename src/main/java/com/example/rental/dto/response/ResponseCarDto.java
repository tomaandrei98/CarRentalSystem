package com.example.rental.dto.response;

import com.example.rental.dto.base.BaseDto;
import com.example.rental.enums.Status;
import com.example.rental.enums.Transmission;
import lombok.*;

import java.util.List;

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
    private Integer seats;
    private Transmission transmission;
    private Integer smallBag;
    private Integer largeBag;
    private Long categoryId;
    private String categoryName;
    private List<Long> rentalsId;
}
