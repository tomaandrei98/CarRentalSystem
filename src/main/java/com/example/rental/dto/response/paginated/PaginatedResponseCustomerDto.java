package com.example.rental.dto.response.paginated;

import com.example.rental.dto.response.ResponseCustomerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedResponseCustomerDto {
    private List<ResponseCustomerDto> customers;
    private Long numberOfItems;
    private Integer numberOfPages;
}
