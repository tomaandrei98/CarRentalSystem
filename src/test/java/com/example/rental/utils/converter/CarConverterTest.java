package com.example.rental.utils.converter;

import com.example.rental.dto.request.RequestCarDto;
import com.example.rental.dto.request.RequestSaveCarDto;
import com.example.rental.dto.response.ResponseCarDto;
import com.example.rental.enums.Status;
import com.example.rental.enums.Transmission;
import com.example.rental.model.Car;
import com.example.rental.model.Category;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CarConverterTest {

    @Test
    public void testConvertModelToResponseDto() {
        Category category = Category.builder()
                .name("SUV")
                .description("Sport Utility Vehicle")
                .cars(new ArrayList<>())
                .build();
        category.setId(1L);

        Car car = Car.builder()
                .make("Toyota")
                .model("Camry")
                .year(2022)
                .imageUrl("image-url")
                .rentalPricePerDay(50.00)
                .status(Status.AVAILABLE)
                .seats(5)
                .transmission(Transmission.AUTOMATIC)
                .smallBag(2)
                .largeBag(3)
                .category(category)
                .rentals(new ArrayList<>())
                .build();
        car.setId(1L);

        ResponseCarDto responseDto = new CarConverter().convertModelToResponseDto(car);

        assertEquals(car.getId(), responseDto.getId());
        assertEquals(car.getMake(), responseDto.getMake());
        assertEquals(car.getModel(), responseDto.getModel());
        assertEquals(car.getYear(), responseDto.getYear());
        assertEquals(car.getImageUrl(), responseDto.getImageUrl());
        assertEquals(car.getRentalPricePerDay(), responseDto.getRentalPricePerDay());
        assertEquals(car.getStatus(), responseDto.getStatus());
        assertEquals(car.getSeats(), responseDto.getSeats());
        assertEquals(car.getTransmission(), responseDto.getTransmission());
        assertEquals(car.getSmallBag(), responseDto.getSmallBag());
        assertEquals(car.getLargeBag(), responseDto.getLargeBag());
        assertEquals(category.getId(), responseDto.getCategoryId());
        assertEquals(category.getName(), responseDto.getCategoryName());
    }

    @Test
    public void testConvertRequestToModel() {
        RequestCarDto requestDto = RequestCarDto.builder()
                .make("Toyota")
                .model("Camry")
                .year(2022)
                .imageUrl("image-url")
                .rentalPricePerDay(50.00)
                .status(Status.AVAILABLE)
                .build();

        Car car = new CarConverter().convertRequestToModel(requestDto);

        assertNull(car.getId());
        assertEquals(requestDto.getMake(), car.getMake());
        assertEquals(requestDto.getModel(), car.getModel());
        assertEquals(requestDto.getYear(), car.getYear());
        assertEquals(requestDto.getImageUrl(), car.getImageUrl());
        assertEquals(requestDto.getRentalPricePerDay(), car.getRentalPricePerDay());
        assertEquals(requestDto.getStatus(), car.getStatus());
        assertNotNull(car.getRentals());
        assertTrue(car.getRentals().isEmpty());
    }

    @Test
    public void testConvertRequestSaveToModel() {
        RequestSaveCarDto requestDto = RequestSaveCarDto.builder()
                .make("Toyota")
                .model("Camry")
                .year(2022)
                .imageUrl("image-url")
                .rentalPricePerDay(50.00)
                .status(Status.AVAILABLE)
                .seats(5)
                .transmission(Transmission.AUTOMATIC)
                .smallBag(2)
                .largeBag(3)
                .build();

        Car car = new CarConverter().convertRequestSaveToModel(requestDto);

        assertNull(car.getId());
        assertEquals(requestDto.getMake(), car.getMake());
        assertEquals(requestDto.getModel(), car.getModel());
        assertEquals(requestDto.getYear(), car.getYear());
        assertEquals(requestDto.getImageUrl(), car.getImageUrl());
        assertEquals(requestDto.getRentalPricePerDay(), car.getRentalPricePerDay());
        assertEquals(requestDto.getStatus(), car.getStatus());
        assertEquals(requestDto.getSeats(), car.getSeats());
        assertEquals(requestDto.getTransmission(), car.getTransmission());
        assertEquals(requestDto.getSmallBag(), car.getSmallBag());
        assertEquals(requestDto.getLargeBag(), car.getLargeBag());
        assertNotNull(car.getRentals());
        assertTrue(car.getRentals().isEmpty());
    }
}