package com.example.rental.utils.converter;

import com.example.rental.dto.request.RequestRentalDto;
import com.example.rental.dto.response.ResponseRentalDto;
import com.example.rental.model.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalConverter {
    public ResponseRentalDto convertModelToResponseDto(Rental rental) {
        ResponseRentalDto response = ResponseRentalDto.builder()
                .startDate(rental.getStartDate())
                .endDate(rental.getEndDate())
                .returned(rental.isReturned())
                .build();
        response.setId(rental.getId());
        return response;
    }

    public Rental convertRequestToModel(RequestRentalDto requestRentalDto) {
        return Rental.builder()
                .startDate(requestRentalDto.getStartDate())
                .endDate(requestRentalDto.getEndDate())
                .returned(requestRentalDto.isReturned())
                .build();
    }
}
