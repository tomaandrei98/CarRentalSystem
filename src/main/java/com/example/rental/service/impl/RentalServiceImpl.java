package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestRentalDto;
import com.example.rental.dto.request.RequestSaveRentalDto;
import com.example.rental.dto.response.ResponseRentalDto;
import com.example.rental.dto.response.paginated.PaginatedResponseRentalDto;
import com.example.rental.exception.CustomerNotFoundException;
import com.example.rental.exception.RentalNotFoundException;
import com.example.rental.exception.SaveRentalNotAccepted;
import com.example.rental.model.Car;
import com.example.rental.model.Customer;
import com.example.rental.model.Rental;
import com.example.rental.repository.CarRepository;
import com.example.rental.repository.CustomerRepository;
import com.example.rental.repository.RentalRepository;
import com.example.rental.security.model.AppUser;
import com.example.rental.security.repository.AppUserRepository;
import com.example.rental.service.RentalService;
import com.example.rental.utils.converter.RentalConverter;
import com.example.rental.utils.email.EmailSenderService;
import com.example.rental.utils.logger.Log;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.rental.utils.MessageGenerator.*;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalConverter rentalConverter;
    private final AppUserRepository appUserRepository;
    private final CarRepository carRepository;
    private final EmailSenderService emailSenderService;

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
    public ResponseRentalDto saveRental(RequestSaveRentalDto requestSaveRentalDto) {
        Rental rentalToSave = rentalConverter.convertRequestSaveToModel(requestSaveRentalDto);

        String appUserEmail = handleAppUserRelationship(rentalToSave);
        handleCarsRelationship(requestSaveRentalDto, rentalToSave);

        Rental savedRental = rentalRepository.save(rentalToSave);
        ResponseRentalDto responseRentalDto = rentalConverter.convertModelToResponseDto(savedRental);
        getEmail(appUserEmail, responseRentalDto);

        return responseRentalDto;
    }

    private void getEmail(String email, ResponseRentalDto responseRentalDto) {
        emailSenderService.sendEmail(email,
                "Rental made successfully.",
                String.format("Rent details:\n\n - start date: %s,\n - end date: %s,\n - total price: %s",
                        responseRentalDto.getStartDate(), responseRentalDto.getEndDate(), responseRentalDto.getTotalPrice()));
    }

    private void handleCarsRelationship(RequestSaveRentalDto requestSaveRentalDto, Rental rentalToSave) {
        List<Car> cars = carRepository.findAllById(requestSaveRentalDto.getCarsId());

        if (cars.isEmpty()) {
            throw new SaveRentalNotAccepted(getSaveRentalNotAcceptedMessage());
        }

        cars.forEach(car -> {
            car.addRental(rentalToSave);
//            car.setStatus(Status.RENTED);
        });
        rentalToSave.setCars(cars);
    }

    private String handleAppUserRelationship(Rental rentalToSave) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        AppUser appUser = appUserRepository.findUserByUsername(currentUsername);

        rentalToSave.setAppUser(appUser);
        appUser.addRental(rentalToSave);

        return appUser.getEmail();
    }

    private void handleAppUserRelationship(String email, Rental rentalToSave) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(getAppUserNotFoundMessage(email)));

        rentalToSave.setAppUser(appUser);
        appUser.addRental(rentalToSave);
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

        rentalToDelete.getCars().forEach(car -> {
            car.removeRental(rentalToDelete);
//            car.setStatus(Status.AVAILABLE);
        });

        rentalRepository.delete(rentalToDelete);
    }

    @Override
    @Log
    public ResponseRentalDto returnRentalById(Long rentalId) {
        Rental rentalToReturn = rentalRepository.findById(rentalId)
                .orElseThrow(
                        () -> new RentalNotFoundException(getRentalNotFoundMessage(rentalId))
                );

        rentalToReturn.setReturned(true);
//        rentalToReturn.getCars().forEach(car -> car.setStatus(Status.AVAILABLE));

        return rentalConverter.convertModelToResponseDto(
                rentalRepository.save(rentalToReturn)
        );
    }

    @Override
    public PaginatedResponseRentalDto getRentalsPaginated(Integer pageNumber, Integer pageSize, String sortBy) {
        Page<Rental> rentalPage = rentalRepository
                .findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));

        return PaginatedResponseRentalDto.builder()
                .rentals(rentalPage.getContent().stream()
                        .map(rentalConverter::convertModelToResponseDto)
                        .toList())
                .numberOfItems(rentalPage.getTotalElements())
                .numberOfPages(rentalPage.getTotalPages())
                .build();
    }
}
