package com.example.rental.dto.response.paginated;

import com.example.rental.dto.response.ResponseCarDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedResponseCarDto {
    private List<ResponseCarDto> cars;
    private Long numberOfItems;
    private Integer numberOfPages;
}
