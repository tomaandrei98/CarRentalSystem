package com.example.rental.utils.converter;

import com.example.rental.dto.request.RequestCategoryDto;
import com.example.rental.dto.response.ResponseCategoryDto;
import com.example.rental.model.Category;
import com.example.rental.model.base.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CategoryConverter {
    public ResponseCategoryDto convertModelToResponseDto(Category category) {
        ResponseCategoryDto response = ResponseCategoryDto.builder()
                .name(category.getName())
                .description(category.getDescription())
                .carsId(category.getCars().stream().map(BaseEntity::getId).toList())
                .build();
        response.setId(category.getId());
        return response;
    }

    public Category convertRequestToModel(RequestCategoryDto requestCategoryDto) {
        return Category.builder()
                .name(requestCategoryDto.getName())
                .description(requestCategoryDto.getDescription())
                .cars(new ArrayList<>())
                .build();
    }
}
