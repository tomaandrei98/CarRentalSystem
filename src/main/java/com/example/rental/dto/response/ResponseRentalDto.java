package com.example.rental.dto.response;

import com.example.rental.dto.base.BaseDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ResponseRentalDto extends BaseDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean returned;
    private Double totalPrice;
    private Long customerId;
    private String customerEmail;
    private List<Long> carsId;
    private List<ResponseCarDto> carsDto;
}
