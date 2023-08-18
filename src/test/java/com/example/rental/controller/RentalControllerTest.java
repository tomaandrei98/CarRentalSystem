package com.example.rental.controller;

import com.example.rental.dto.request.RequestRentalDto;
import com.example.rental.dto.request.RequestSaveRentalDto;
import com.example.rental.dto.response.ResponseRentalDto;
import com.example.rental.dto.response.paginated.PaginatedResponseRentalDto;
import com.example.rental.service.RentalService;
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

class RentalControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RentalService rentalService;

    @InjectMocks
    private RentalController rentalController;

    private AutoCloseable closeable;

    @BeforeEach
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(rentalController)
                .build();
    }

    @Test
    public void getAllRentalsTest() throws Exception {
        List<ResponseRentalDto> rentals = new ArrayList<>();
        rentals.add(getRentalOne());
        rentals.add(getRentalTwo());

        when(rentalService.getAllRentals()).thenReturn(rentals);

        mockMvc.perform(get("/api/v1/rentals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].returned", is(false)))
                .andExpect(jsonPath("$.data[0].totalPrice", is(100.00)))
                .andExpect(jsonPath("$.data[1].returned", is(false)))
                .andExpect(jsonPath("$.data[1].totalPrice", is(200.00)))
                .andExpect(jsonPath("$.message", is("Rentals downloaded successfully.")));

        verify(rentalService).getAllRentals();
        verifyNoMoreInteractions(rentalService);
    }

    @Test
    public void getRentalById() throws Exception {
        var response = getRentalOne();

        when(rentalService.getRentalById(anyLong())).thenReturn(response);

        mockMvc.perform(get("/api/v1/rentals/{rentalId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.returned", is(false)))
                .andExpect(jsonPath("$.data.totalPrice", is(100.00)))
                .andExpect(jsonPath("$.message", is("Rental downloaded successfully.")));

        verify(rentalService).getRentalById(anyLong());
        verifyNoMoreInteractions(rentalService);
    }

    @Test
    public void returnRentalById() throws Exception {
        var response = getRentalThree();

        when(rentalService.returnRentalById(anyLong())).thenReturn(response);

        mockMvc.perform(get("/api/v1/rentals/return/{rentalId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.returned", is(true)))
                .andExpect(jsonPath("$.data.totalPrice", is(500.00)))
                .andExpect(jsonPath("$.message", is("Rental returned successfully.")));

        verify(rentalService).returnRentalById(anyLong());
        verifyNoMoreInteractions(rentalService);
    }

    @Test
    public void getRentalsPaginated() throws Exception {
        List<ResponseRentalDto> rentals = new ArrayList<>();
        rentals.add(getRentalOne());
        rentals.add(getRentalTwo());

        var response = PaginatedResponseRentalDto.builder()
                .rentals(rentals)
                .numberOfItems(2L)
                .numberOfPages(1)
                .build();

        when(rentalService.getRentalsPaginated(anyInt(), anyInt(), anyString())).thenReturn(response);

        mockMvc.perform(get("/api/v1/rentals/paginated"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.rentals[0].returned", is(false)))
                .andExpect(jsonPath("$.data.rentals[0].totalPrice", is(100.00)))
                .andExpect(jsonPath("$.data.rentals[1].returned", is(false)))
                .andExpect(jsonPath("$.data.rentals[1].totalPrice", is(200.00)))
                .andExpect(jsonPath("$.data.numberOfItems", is(2)))
                .andExpect(jsonPath("$.data.numberOfPages", is(1)))
                .andExpect(jsonPath("$.message", is("Rentals downloaded successfully.")));

        verify(rentalService).getRentalsPaginated(anyInt(), anyInt(), anyString());
        verifyNoMoreInteractions(rentalService);
    }

    @Test
    public void addRentalTest() throws Exception {
        var request = RequestSaveRentalDto.builder()
                .returned(false)
                .email("email@gmail.com")
                .build();

        var response = getRentalOne();

        when(rentalService.saveRental(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/rentals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.returned", is(false)))
                .andExpect(jsonPath("$.data.totalPrice", is(100.00)))
                .andExpect(jsonPath("$.message", is("Rental created successfully.")));

        verify(rentalService).saveRental(eq(request));
        verifyNoMoreInteractions(rentalService);
    }

    @Test
    public void updateRentalTest() throws Exception {
        var request = RequestRentalDto.builder()
                .returned(false)
                .build();

        var response = getRentalOne();

        when(rentalService.updateRental(anyLong(), eq(request))).thenReturn(response);

        mockMvc.perform(put("/api/v1/rentals/{rentalId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.returned", is(false)))
                .andExpect(jsonPath("$.data.totalPrice", is(100.00)))
                .andExpect(jsonPath("$.message", is("Rental updated successfully.")));

        verify(rentalService).updateRental(anyLong(), eq(request));
        verifyNoMoreInteractions(rentalService);
    }

    @Test
    public void deleteRentalTest() throws Exception {
        mockMvc.perform(delete("/api/v1/rentals/{rentalId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Rental deleted successfully.")));

        verify(rentalService).deleteRentalById(anyLong());
        verifyNoMoreInteractions(rentalService);
    }

    private ResponseRentalDto getRentalOne() {
        return ResponseRentalDto.builder()
                .returned(false)
                .totalPrice(100.00)
                .build();
    }

    private ResponseRentalDto getRentalTwo() {
        return ResponseRentalDto.builder()
                .returned(false)
                .totalPrice(200.00)
                .build();
    }

    private ResponseRentalDto getRentalThree() {
        return ResponseRentalDto.builder()
                .returned(true)
                .totalPrice(500.00)
                .build();
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