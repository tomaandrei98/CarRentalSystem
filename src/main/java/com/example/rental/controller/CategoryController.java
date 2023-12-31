package com.example.rental.controller;

import com.example.rental.dto.request.RequestCategoryDto;
import com.example.rental.dto.response.ResponseCategoryDto;
import com.example.rental.dto.response.general.ApiResponse;
import com.example.rental.dto.response.paginated.PaginatedResponseCategoryDto;
import com.example.rental.service.CategoryService;
import com.example.rental.utils.logger.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Log
    public ResponseEntity<ApiResponse<List<ResponseCategoryDto>>> getAllCategories() {
        return ResponseEntity.ok(
                new ApiResponse<>(categoryService.getAllCategories(), "Categories downloaded successfully.")
        );
    }

    @GetMapping("/{categoryId}")
    @Log
    public ResponseEntity<ApiResponse<ResponseCategoryDto>> getCategoryById(
            @PathVariable("categoryId") Long categoryId
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(categoryService.getCategoryById(categoryId),
                        "Category downloaded successfully.")
        );
    }

    @GetMapping("/paginated")
    @Log
    public ResponseEntity<ApiResponse<PaginatedResponseCategoryDto>> getCategoriesPaginated(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "name") String sortBy
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(categoryService.getCategoriesPaginated(pageNumber, pageSize, sortBy),
                        "Categories downloaded successfully.")
        );
    }

    @PostMapping
    @Log
    public ResponseEntity<ApiResponse<ResponseCategoryDto>> addCategory(
            @RequestBody RequestCategoryDto requestCategoryDto
    ) {

        return ResponseEntity.status(CREATED)
                .body(new ApiResponse<>(categoryService.saveCategory(requestCategoryDto),
                        "Category created successfully."));
    }

    @PutMapping("/{categoryId}")
    @Log
    public ResponseEntity<ApiResponse<ResponseCategoryDto>> updateCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody RequestCategoryDto requestCategoryDto
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(categoryService.updateCategory(categoryId, requestCategoryDto),
                        "Category updated successfully.")
        );
    }

    @DeleteMapping("/{categoryId}")
    @Log
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategoryById(categoryId);

        return ResponseEntity.ok(
                new ApiResponse<>("Category deleted successfully.")
        );
    }

    @GetMapping("/populate")
    @Log
    public ResponseEntity<ApiResponse<Void>> populateWithCategories() {
        categoryService.populateWithCategories();

        return ResponseEntity.ok(
                new ApiResponse<>("Category added successfully")
        );
    }
}
