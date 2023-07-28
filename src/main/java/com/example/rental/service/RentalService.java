package com.example.rental.service;

import com.example.rental.dto.request.RequestRentalDto;
import com.example.rental.dto.response.ResponseRentalDto;

import java.util.List;

public interface RentalService {
    List<ResponseRentalDto> getAllRentals();

    ResponseRentalDto getRentalById(Long rentalId);

    ResponseRentalDto saveRental(Long customerId, RequestRentalDto requestRentalDto);

    ResponseRentalDto updateRental(Long rentalId, RequestRentalDto requestRentalDto);

    void deleteRentalById(Long rentalId);
}
