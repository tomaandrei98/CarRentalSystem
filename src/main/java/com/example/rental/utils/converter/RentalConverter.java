package com.example.rental.utils.converter;

import com.example.rental.dto.request.RequestRentalDto;
import com.example.rental.dto.request.RequestSaveRentalDto;
import com.example.rental.dto.response.ResponseRentalDto;
import com.example.rental.model.Rental;
import com.example.rental.model.base.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.example.rental.utils.RentalPriceCalculator.calculatePriceForRental;

@Component
public class RentalConverter {
    public ResponseRentalDto convertModelToResponseDto(Rental rental) {
        ResponseRentalDto response = ResponseRentalDto.builder()
                .startDate(rental.getStartDate())
                .endDate(rental.getEndDate())
                .returned(rental.isReturned())
                .totalPrice(calculatePriceForRental(rental))
                .customerId(rental.getCustomer().getId())
                .carsId(rental.getCars().stream().map(BaseEntity::getId).toList())
                .build();
        response.setId(rental.getId());
        return response;
    }

    public Rental convertRequestToModel(RequestRentalDto requestRentalDto) {
        return Rental.builder()
                .startDate(requestRentalDto.getStartDate())
                .endDate(requestRentalDto.getEndDate())
                .returned(false)
                .cars(new ArrayList<>())
                .build();
    }

    public Rental convertRequestSaveToModel(RequestSaveRentalDto requestSaveRentalDto) {
        return Rental.builder()
                .startDate(requestSaveRentalDto.getStartDate())
                .endDate(requestSaveRentalDto.getEndDate())
                .returned(false)
                .cars(new ArrayList<>())
                .build();
    }
}
