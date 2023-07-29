package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestRentalDto;
import com.example.rental.dto.response.ResponseRentalDto;
import com.example.rental.exception.CustomerNotFoundException;
import com.example.rental.exception.RentalNotFoundException;
import com.example.rental.exception.SaveRentalNotAccepted;
import com.example.rental.model.Car;
import com.example.rental.model.Customer;
import com.example.rental.model.Rental;
import com.example.rental.repository.CarRepository;
import com.example.rental.repository.CustomerRepository;
import com.example.rental.repository.RentalRepository;
import com.example.rental.service.RentalService;
import com.example.rental.utils.converter.RentalConverter;
import com.example.rental.utils.logger.Log;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.rental.utils.MessageGenerator.*;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalConverter rentalConverter;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    @Override
    @Log
    public List<ResponseRentalDto> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(rentalConverter::convertModelToResponseDto)
                .toList();
    }

    @Override
    @Log
    public ResponseRentalDto getRentalById(Long rentalId) {
        Rental downloadedRental = rentalRepository.findById(rentalId)
                .orElseThrow(
                        () -> new RentalNotFoundException(getRentalNotFoundMessage(rentalId))
                );

        return rentalConverter.convertModelToResponseDto(downloadedRental);
    }

    @Override
    @Log
    public ResponseRentalDto saveRental(Long customerId, RequestRentalDto requestRentalDto) {
        Rental rentalToSave = rentalConverter.convertRequestToModel(requestRentalDto);

        handleCustomerRelationship(customerId, rentalToSave);

        handleCarsRelationship(requestRentalDto, rentalToSave);

        Rental savedRental = rentalRepository.save(rentalToSave);
        return rentalConverter.convertModelToResponseDto(savedRental);
    }

    private void handleCarsRelationship(RequestRentalDto requestRentalDto, Rental rentalToSave) {
        List<Car> cars = carRepository.findAllById(requestRentalDto.getCarsId());

        if (cars.isEmpty()) {
            throw new SaveRentalNotAccepted(getSaveRentalNotAcceptedMessage());
        }

        cars.forEach(car -> car.addRental(rentalToSave));
        rentalToSave.setCars(cars);
    }

    private void handleCustomerRelationship(Long customerId, Rental rentalToSave) {
        Customer savedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(getCustomerNotFoundMessage(customerId)));
        rentalToSave.setCustomer(savedCustomer);
        savedCustomer.addRental(rentalToSave);
    }

    @Override
    @Log
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
    @Log
    public void deleteRentalById(Long rentalId) {
        Rental rentalToDelete = rentalRepository.findById(rentalId)
                .orElseThrow(
                        () -> new RentalNotFoundException(getRentalNotFoundMessage(rentalId))
                );

        rentalToDelete.getCars()
                .forEach(car -> car.removeRental(rentalToDelete));

        rentalRepository.delete(rentalToDelete);
    }
}
