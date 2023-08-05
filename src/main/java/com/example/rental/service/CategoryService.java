package com.example.rental.service;

import com.example.rental.dto.request.RequestCategoryDto;
import com.example.rental.dto.response.ResponseCategoryDto;
import com.example.rental.dto.response.paginated.PaginatedResponseCategoryDto;

import java.util.List;

public interface CategoryService {
    List<ResponseCategoryDto> getAllCategories();

    ResponseCategoryDto getCategoryById(Long categoryId);

    ResponseCategoryDto saveCategory(RequestCategoryDto requestCategoryDto);

    ResponseCategoryDto updateCategory(Long categoryId, RequestCategoryDto requestCategoryDto);

    void deleteCategoryById(Long categoryId);

    PaginatedResponseCategoryDto getCategoriesPaginated(Integer pageNumber, Integer pageSize, String sortBy);
}
