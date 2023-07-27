package com.example.rental.dto.request;

import com.example.rental.dto.base.BaseDto;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class RequestRentalDto extends BaseDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean returned;
}
