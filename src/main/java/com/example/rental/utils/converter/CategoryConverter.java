package com.example.rental.utils.converter;

import com.example.rental.dto.request.RequestCategoryDto;
import com.example.rental.dto.response.ResponseCategoryDto;
import com.example.rental.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public ResponseCategoryDto convertModelToResponseDto(Category category) {
        ResponseCategoryDto response = ResponseCategoryDto.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
        response.setId(category.getId());
        return response;
    }

    public Category convertRequestToModel(RequestCategoryDto requestCategoryDto) {
        return Category.builder()
                .name(requestCategoryDto.getName())
                .description(requestCategoryDto.getDescription())
                .build();
    }
}
