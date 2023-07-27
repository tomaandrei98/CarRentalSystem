package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestCategoryDto;
import com.example.rental.dto.response.ResponseCategoryDto;
import com.example.rental.exception.CategoryNotFoundException;
import com.example.rental.model.Category;
import com.example.rental.repository.CategoryRepository;
import com.example.rental.service.CategoryService;
import com.example.rental.utils.converter.CategoryConverter;
import com.example.rental.utils.logger.Log;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.rental.utils.MessageGenerator.getCategoryNotFoundMessage;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    @Log
    public List<ResponseCategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryConverter::convertModelToResponseDto)
                .toList();
    }

    @Override
    @Log
    public ResponseCategoryDto getCategoryById(Long categoryId) {
        Category downloadedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new CategoryNotFoundException(getCategoryNotFoundMessage(categoryId))
                );

        return categoryConverter.convertModelToResponseDto(downloadedCategory);
    }

    @Override
    @Log
    public ResponseCategoryDto saveCategory(RequestCategoryDto requestCategoryDto) {
        Category categoryToSave = categoryConverter.convertRequestToModel(requestCategoryDto);
        Category savedCategory = categoryRepository.save(categoryToSave);
        return categoryConverter.convertModelToResponseDto(savedCategory);
    }

    @Override
    @Log
    @Transactional
    public ResponseCategoryDto updateCategory(Long categoryId, RequestCategoryDto requestCategoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        optionalCategory
                .orElseThrow(
                        () -> new CategoryNotFoundException(getCategoryNotFoundMessage(categoryId))
                );

        optionalCategory.ifPresent(updateCategory -> {
            updateCategory.setName(requestCategoryDto.getName());
            updateCategory.setDescription(requestCategoryDto.getDescription());
        });

        return categoryConverter.convertModelToResponseDto(
                categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new CategoryNotFoundException(getCategoryNotFoundMessage(categoryId)))
        );
    }

    @Override
    @Log
    public void deleteCategoryById(Long categoryId) {
        Category categoryToDelete = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new CategoryNotFoundException(getCategoryNotFoundMessage(categoryId))
                );

        categoryRepository.delete(categoryToDelete);
    }
}
