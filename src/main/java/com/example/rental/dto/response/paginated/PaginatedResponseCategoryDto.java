package com.example.rental.dto.response.paginated;

import com.example.rental.dto.response.ResponseCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedResponseCategoryDto {
    private List<ResponseCategoryDto> categories;
    private Long numberOfItems;
    private Integer numberOfPages;
}
