package com.example.rental.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestRentalDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean returned;
    private List<Long> carsId;
}
