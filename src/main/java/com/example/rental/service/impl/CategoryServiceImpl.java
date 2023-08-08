package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestCategoryDto;
import com.example.rental.dto.response.ResponseCategoryDto;
import com.example.rental.dto.response.paginated.PaginatedResponseCategoryDto;
import com.example.rental.exception.CategoryNotFoundException;
import com.example.rental.exception.DeleteCategoryNotAccepted;
import com.example.rental.model.Category;
import com.example.rental.repository.CategoryRepository;
import com.example.rental.service.CategoryService;
import com.example.rental.utils.converter.CategoryConverter;
import com.example.rental.utils.logger.Log;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.rental.utils.MessageGenerator.getCategoryNotFoundMessage;
import static com.example.rental.utils.MessageGenerator.getDeleteCategoryNotAcceptedMessage;

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

        if (!categoryToDelete.getCars().isEmpty()) {
            throw new DeleteCategoryNotAccepted(getDeleteCategoryNotAcceptedMessage(categoryId));
        }

        categoryRepository.delete(categoryToDelete);
    }

    @Override
    @Log
    public PaginatedResponseCategoryDto getCategoriesPaginated(Integer pageNumber, Integer pageSize, String sortBy) {
        Page<Category> categoryPage = categoryRepository
                .findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));

        return PaginatedResponseCategoryDto.builder()
                .categories(categoryPage.getContent().stream()
                        .map(categoryConverter::convertModelToResponseDto)
                        .toList())
                .numberOfItems(categoryPage.getTotalElements())
                .numberOfPages(categoryPage.getTotalPages())
                .build();
    }

    @Override
    public void populateWithCategories() {
        categoryRepository.save(Category.builder().name("Small").description("small").cars(new ArrayList<>()).build());
        categoryRepository.save(Category.builder().name("Medium").description("medium").cars(new ArrayList<>()).build());
        categoryRepository.save(Category.builder().name("Large").description("large").cars(new ArrayList<>()).build());
        categoryRepository.save(Category.builder().name("Estate").description("estate").cars(new ArrayList<>()).build());
        categoryRepository.save(Category.builder().name("Premium").description("premium").cars(new ArrayList<>()).build());
        categoryRepository.save(Category.builder().name("People carriers").description("people carriers").cars(new ArrayList<>()).build());
        categoryRepository.save(Category.builder().name("SUVs").description("SUVs").cars(new ArrayList<>()).build());
    }
}
