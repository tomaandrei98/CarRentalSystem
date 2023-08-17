package com.example.rental.controller;

import com.example.rental.dto.request.RequestSaveCarDto;
import com.example.rental.dto.request.RequestUpdateCarDto;
import com.example.rental.dto.response.ResponseCarDto;
import com.example.rental.dto.response.paginated.PaginatedResponseCarDto;
import com.example.rental.enums.Status;
import com.example.rental.enums.Transmission;
import com.example.rental.service.CarService;
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

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private AutoCloseable closeable;

    @BeforeEach
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(carController)
                .build();
    }

    @Test
    public void getAllCarsTest() throws Exception {
        List<ResponseCarDto> cars = new ArrayList<>();
        cars.add(getCarOne());
        cars.add(getCarTwo());

        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].make", is("Make1")))
                .andExpect(jsonPath("$.data[1].make", is("Make2")))
                .andExpect(jsonPath("$.message", is("Cars downloaded successfully.")));

        verify(carService).getAllCars();
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void getAvailableCarsTest() throws Exception {
        List<ResponseCarDto> cars = new ArrayList<>();
        cars.add(getCarOne());
        cars.add(getCarTwo());

        when(carService.getAvailableCars(any(), any())).thenReturn(cars);

        mockMvc.perform(get("/api/v1/cars/available")
                        .param("startDate", LocalDate.of(2020, Month.SEPTEMBER, 10).toString())
                        .param("endDate", LocalDate.of(2020, Month.SEPTEMBER, 12).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].make", is("Make1")))
                .andExpect(jsonPath("$.data[1].make", is("Make2")))
                .andExpect(jsonPath("$.message", is("Available cars downloaded successfully.")));

        verify(carService).getAvailableCars(any(), any());
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void getCarByIdTest() throws Exception {
        var response = getCarOne();

        when(carService.getCarById(anyLong())).thenReturn(response);

        mockMvc.perform(get("/api/v1/cars/{carId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.make", is("Make1")))
                .andExpect(jsonPath("$.message", is("Car downloaded successfully.")));

        verify(carService).getCarById(anyLong());
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void getCarsByMatchingNameTest() throws Exception {
        List<ResponseCarDto> response = new ArrayList<>();
        response.add(getCarOne());
        response.add(getCarTwo());

        when(carService.getCarsByMatchingName(anyString())).thenReturn(response);

        mockMvc.perform(get("/api/v1/cars/filter")
                        .param("matchingName", "ake"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].make", is("Make1")))
                .andExpect(jsonPath("$.data[1].make", is("Make2")))
                .andExpect(jsonPath("$.message", is("Cars downloaded successfully.")));

        verify(carService).getCarsByMatchingName(anyString());
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void getCarsPaginated() throws Exception {
        List<ResponseCarDto> cars = new ArrayList<>();
        cars.add(getCarOne());
        cars.add(getCarTwo());

        var response = new PaginatedResponseCarDto(cars, 2L, 1);

        when(carService.getCarsPaginated(anyInt(), anyInt(), anyString())).thenReturn(response);

        mockMvc.perform(get("/api/v1/cars/paginated")
                        .param("pageNumber", "0")
                        .param("pageSize", "0")
                        .param("sortBy", "make"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.cars[0].make", is("Make1")))
                .andExpect(jsonPath("$.data.numberOfItems", is(2)))
                .andExpect(jsonPath("$.data.numberOfPages", is(1)))
                .andExpect(jsonPath("$.message", is("Cars downloaded successfully.")));

        verify(carService).getCarsPaginated(anyInt(), anyInt(), anyString());
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void getCarByCategoryIdTest() throws Exception {
        var response = getCarOne();

        when(carService.getCarByCategoryId(response.getCategoryId())).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/cars/category/{categoryId}", response.getCategoryId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].make", is("Make1")))
                .andExpect(jsonPath("$.message", is("Cars downloaded successfully.")));

        verify(carService).getCarByCategoryId(response.getCategoryId());
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void saveCarTest() throws Exception {
        var request = RequestSaveCarDto.builder()
                .make("Make1")
                .build();
        var response = ResponseCarDto.builder()
                .make("Make1")
                .build();

        when(carService.saveCar(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.make", is("Make1")))
                .andExpect(jsonPath("$.message", is("Car created successfully.")));

        verify(carService).saveCar(request);
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void updateCarTest() throws Exception {
        var request = RequestUpdateCarDto.builder()
                .make("Make1Updated")
                .build();
        var response = ResponseCarDto.builder()
                .make("Make1Updated")
                .build();

        when(carService.updateCar(request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.make", is("Make1Updated")))
                .andExpect(jsonPath("$.message", is("Car updated successfully.")));

        verify(carService).updateCar(request);
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void deleteCarTest() throws Exception {
        long carId = 1L;

        mockMvc.perform(delete("/api/v1/cars/{carId}", carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Car deleted successfully.")));

        verify(carService).deleteCarById(carId);
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void populateWithCarsTest() throws Exception {
        mockMvc.perform(get("/api/v1/cars/populate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Cars added successfully")));

        verify(carService).populateWithCars();
        verifyNoMoreInteractions(carService);
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

    private ResponseCarDto getCarOne() {
        return ResponseCarDto.builder()
                .make("Make1")
                .model("Model1")
                .year(2020)
                .imageUrl("url1")
                .rentalPricePerDay(10.00)
                .status(Status.AVAILABLE)
                .seats(5)
                .transmission(Transmission.MANUAL)
                .smallBag(1)
                .largeBag(1)
                .categoryId(1L)
                .categoryName("Category1")
                .rentalsId(new ArrayList<>())
                .build();
    }

    private ResponseCarDto getCarTwo() {
        return ResponseCarDto.builder()
                .make("Make2")
                .model("Model2")
                .year(2020)
                .imageUrl("url2")
                .rentalPricePerDay(10.00)
                .status(Status.AVAILABLE)
                .seats(5)
                .transmission(Transmission.MANUAL)
                .smallBag(1)
                .largeBag(1)
                .categoryId(2L)
                .categoryName("Category2")
                .rentalsId(new ArrayList<>())
                .build();
    }
}