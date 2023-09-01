package com.example.rental.utils.converter;

import com.example.rental.dto.request.RequestCategoryDto;
import com.example.rental.dto.response.ResponseCategoryDto;
import com.example.rental.model.Category;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CategoryConverterTest {

    @Test
    public void testConvertModelToResponseDto() {
        Category category = Category.builder()
                .name("SUV")
                .description("Sport Utility Vehicle")
                .cars(new ArrayList<>())
                .build();
        category.setId(1L);

        ResponseCategoryDto responseDto = new CategoryConverter().convertModelToResponseDto(category);

        assertEquals(category.getId(), responseDto.getId());
        assertEquals(category.getName(), responseDto.getName());
        assertEquals(category.getDescription(), responseDto.getDescription());
    }

    @Test
    public void testConvertRequestToModel() {
        RequestCategoryDto requestDto = RequestCategoryDto.builder()
                .name("Sedan")
                .description("Four-door car")
                .build();

        Category category = new CategoryConverter().convertRequestToModel(requestDto);

        assertNull(category.getId());
        assertEquals(requestDto.getName(), category.getName());
        assertEquals(requestDto.getDescription(), category.getDescription());
        assertNotNull(category.getCars());
        assertTrue(category.getCars().isEmpty());
    }
}