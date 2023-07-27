package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestRentalDto;
import com.example.rental.dto.response.ResponseRentalDto;
import com.example.rental.exception.RentalNotFoundException;
import com.example.rental.model.Rental;
import com.example.rental.repository.RentalRepository;
import com.example.rental.service.RentalService;
import com.example.rental.utils.converter.RentalConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.rental.utils.MessageGenerator.getRentalNotFoundMessage;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalConverter rentalConverter;

    @Override
    public List<ResponseRentalDto> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(rentalConverter::convertModelToResponseDto)
                .toList();
    }

    @Override
    public ResponseRentalDto getRentalById(Long rentalId) {
        Rental downloadedRental = rentalRepository.findById(rentalId)
                .orElseThrow(
                        () -> new RentalNotFoundException(getRentalNotFoundMessage(rentalId))
                );

        return rentalConverter.convertModelToResponseDto(downloadedRental);
    }

    @Override
    public ResponseRentalDto saveRental(RequestRentalDto requestRentalDto) {
        Rental rentalToSave = rentalConverter.convertRequestToModel(requestRentalDto);
        Rental savedRental = rentalRepository.save(rentalToSave);
        return rentalConverter.convertModelToResponseDto(savedRental);
    }

    @Override
    @Transactional
    public ResponseRentalDto updateRental(Long rentalId, RequestRentalDto requestRentalDto) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);

        optionalRental
                .orElseThrow(
                        () -> new RentalNotFoundException(getRentalNotFoundMessage(rentalId))
                );

        optionalRental.ifPresent(updateRental -> {
            updateRental.setStartDate(requestRentalDto.getStartDate());
            updateRental.setEndDate(requestRentalDto.getEndDate());
            updateRental.setReturned(requestRentalDto.isReturned());
        });

        return rentalConverter.convertModelToResponseDto(
                rentalRepository.findById(rentalId)
                        .orElseThrow(
                                () -> new RentalNotFoundException(getRentalNotFoundMessage(rentalId))
                        )
        );
    }

    @Override
    public void deleteRentalById(Long rentalId) {
        Rental rentalToDelete = rentalRepository.findById(rentalId)
                .orElseThrow(
                        () -> new RentalNotFoundException(getRentalNotFoundMessage(rentalId))
                );

        rentalRepository.delete(rentalToDelete);
    }
}
