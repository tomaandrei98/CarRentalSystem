package com.example.rental.dto.response.paginated;

import com.example.rental.dto.response.ResponseRentalDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedResponseRentalDto {
    private List<ResponseRentalDto> rentals;
    private Long numberOfItems;
    private Integer numberOfPages;
}
