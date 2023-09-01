package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestCategoryDto;
import com.example.rental.dto.response.ResponseCategoryDto;
import com.example.rental.model.Category;
import com.example.rental.repository.CategoryRepository;
import com.example.rental.utils.converter.CategoryConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryConverter categoryConverter;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private AutoCloseable closeable;


    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllCategoriesTest() {
        List<Category> categories = new ArrayList<>();

        Category category = Category.builder()
                .name("Test Category")
                .build();

        categories.add(category);

        List<ResponseCategoryDto> expectedResult = new ArrayList<>();
        expectedResult.add(getResponseCategoryDto());

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryConverter.convertModelToResponseDto(any())).thenReturn(getResponseCategoryDto());
        List<ResponseCategoryDto> actualResult = categoryService.getAllCategories();

        assertEquals(expectedResult, actualResult);
        verify(categoryRepository).findAll();
    }

    private Category getCategoryInstance(Long id) {
        Category category = Category.builder()
                .name("Category Name")
                .description("Category Description")
                .cars(new ArrayList<>())
                .build();

        category.setId(id);
        return category;
    }

    private RequestCategoryDto getRequestCategoryDto() {
        return RequestCategoryDto.builder()
                .name("Category Name")
                .description("Category Description")
                .build();
    }

    private ResponseCategoryDto getResponseCategoryDto() {
        return ResponseCategoryDto.builder()
                .name("Category Name")
                .description("Category Description")
                .build();
    }

    @AfterEach
    public void destroy() throws Exception {
        closeable.close();
    }
}