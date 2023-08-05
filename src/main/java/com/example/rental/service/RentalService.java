package com.example.rental.service;

import com.example.rental.dto.request.RequestRentalDto;
import com.example.rental.dto.request.RequestSaveRentalDto;
import com.example.rental.dto.response.ResponseRentalDto;
import com.example.rental.dto.response.paginated.PaginatedResponseRentalDto;

import java.util.List;

public interface RentalService {
    List<ResponseRentalDto> getAllRentals();

    ResponseRentalDto getRentalById(Long rentalId);

    ResponseRentalDto saveRental(RequestSaveRentalDto requestSaveRentalDto);

    ResponseRentalDto updateRental(Long rentalId, RequestRentalDto requestRentalDto);

    void deleteRentalById(Long rentalId);

    ResponseRentalDto returnRentalById(Long rentalId);

    PaginatedResponseRentalDto getRentalsPaginated(Integer pageNumber, Integer pageSize, String sortBy);
}
