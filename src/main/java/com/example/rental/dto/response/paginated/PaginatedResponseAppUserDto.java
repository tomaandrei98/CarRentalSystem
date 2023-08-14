package com.example.rental.dto.response.paginated;

import com.example.rental.dto.response.ResponseAppUserDto;
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
public class PaginatedResponseAppUserDto {
    private List<ResponseAppUserDto> users;
    private Long numberOfItems;
    private Integer numberOfPages;
}
