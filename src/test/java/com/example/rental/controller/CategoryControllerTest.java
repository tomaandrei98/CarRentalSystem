package com.example.rental.controller;

import com.example.rental.dto.request.RequestCategoryDto;
import com.example.rental.dto.response.ResponseCategoryDto;
import com.example.rental.dto.response.paginated.PaginatedResponseCategoryDto;
import com.example.rental.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private AutoCloseable closeable;

    @BeforeEach
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .build();
    }

    @Test
    public void getAllCategoriesTest() throws Exception {
        List<ResponseCategoryDto> categories = new ArrayList<>();
        categories.add(new ResponseCategoryDto("Category1", "Description1", new ArrayList<>()));
        categories.add(new ResponseCategoryDto("Category2", "Description2", new ArrayList<>()));

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name", is("Category1")))
                .andExpect(jsonPath("$.data[1].name", is("Category2")))
                .andExpect(jsonPath("$.message", is("Categories downloaded successfully.")));

        verify(categoryService, times(1)).getAllCategories();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getCategoryByIdTest() throws Exception {
        var response = new ResponseCategoryDto("Category1", "Description1", new ArrayList<>());

        when(categoryService.getCategoryById(anyLong())).thenReturn(response);

        mockMvc.perform(get("/api/v1/categories/{categoryId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Category1")))
                .andExpect(jsonPath("$.message", is("Category downloaded successfully.")));

        verify(categoryService, times(1)).getCategoryById(anyLong());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getCategoriesPaginatedTest() throws Exception {
        List<ResponseCategoryDto> categories = new ArrayList<>();
        categories.add(new ResponseCategoryDto("Category1", "Description1", new ArrayList<>()));
        categories.add(new ResponseCategoryDto("Category2", "Description2", new ArrayList<>()));

        var response = new PaginatedResponseCategoryDto(categories, 2L, 1);

        when(categoryService.getCategoriesPaginated(anyInt(), anyInt(), anyString())).thenReturn(response);

        mockMvc.perform(get("/api/v1/categories/paginated")
                        .param("pageNumber", "0")
                        .param("pageSize", "0")
                        .param("sortBy", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.categories[0].name", is("Category1")))
                .andExpect(jsonPath("$.data.categories[0].description", is("Description1")))
                .andExpect(jsonPath("$.data.categories[1].name", is("Category2")))
                .andExpect(jsonPath("$.data.numberOfItems", is(2)))
                .andExpect(jsonPath("$.data.numberOfPages", is(1)))
                .andExpect(jsonPath("$.message", is("Categories downloaded successfully.")));

        verify(categoryService).getCategoriesPaginated(anyInt(), anyInt(), anyString());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void addCategoryTest() throws Exception {
        var request = new RequestCategoryDto("Category1", "Description1");
        var response = new ResponseCategoryDto("Category1", "Description1", new ArrayList<>());

        when(categoryService.saveCategory(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name", is("Category1")))
                .andExpect(jsonPath("$.data.description", is("Description1")))
                .andExpect(jsonPath("$.message", is("Category created successfully.")));

        verify(categoryService).saveCategory(request);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateCategoryTest() throws Exception {
        var request = new RequestCategoryDto("Category1Updated", "Description1Updated");
        var response = new ResponseCategoryDto("Category1Updated", "Description1Updated", new ArrayList<>());

        when(categoryService.updateCategory(anyLong(), eq(request))).thenReturn(response);

        mockMvc.perform(put("/api/v1/categories/{categoryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Category1Updated")))
                .andExpect(jsonPath("$.data.description", is("Description1Updated")))
                .andExpect(jsonPath("$.message", is("Category updated successfully.")));

        verify(categoryService).updateCategory(anyLong(), eq(request));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void deleteCategoryTest() throws Exception {
        long categoryId = 1L;

        mockMvc.perform(delete("/api/v1/categories/{categoryId}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Category deleted successfully.")));

        verify(categoryService).deleteCategoryById(categoryId);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void populateWithCategoriesTest() throws Exception {
        mockMvc.perform(get("/api/v1/categories/populate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Category added successfully")));

        verify(categoryService).populateWithCategories();
        verifyNoMoreInteractions(categoryService);
    }

    @AfterEach
    public void destroy() throws Exception {
        closeable.close();
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}