package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestAppUserDto;
import com.example.rental.dto.response.ResponseAppUserDto;
import com.example.rental.dto.response.paginated.PaginatedResponseAppUserDto;
import com.example.rental.exception.AppUserNotFoundException;
import com.example.rental.exception.EmailAlreadyTakenException;
import com.example.rental.model.Rental;
import com.example.rental.repository.RentalRepository;
import com.example.rental.security.model.AppUser;
import com.example.rental.security.model.RoleName;
import com.example.rental.security.repository.AppUserRepository;
import com.example.rental.service.AppUserService;
import com.example.rental.utils.converter.AppUserConverter;
import com.example.rental.utils.logger.Log;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.rental.utils.MessageGenerator.getAppUserNotFoundMessage;
import static com.example.rental.utils.MessageGenerator.getEmailAlreadyTakenMessage;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserConverter appUserConverter;
    private final RentalRepository rentalRepository;


    @Override
    @Log
    public List<ResponseAppUserDto> getAllUsers() {
        return appUserRepository.findAll().stream()
                .map(appUserConverter::convertModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Log
    public ResponseAppUserDto findUserByUsername(String username) {
        return appUserConverter.convertModelToDto(
                appUserRepository.findUserByUsername(username)
        );
    }

    @Override
    @Log
    @Transactional
    public void deleteUserByUsername(String username) {
        AppUser user = appUserRepository.findUserByUsername(username);

        List<Rental> rentals = user.getRentals().stream().toList();
        user.setRentals(null);
        rentals.forEach(rental -> rental.setAppUser(null));

        rentalRepository.saveAll(rentals);
        appUserRepository.delete(user);
    }

    @Override
    @Log
    public ResponseAppUserDto getUserById(Long userId) {
        return appUserConverter.convertModelToDto(
                appUserRepository.findById(userId)
                        .orElseThrow(() -> new AppUserNotFoundException(getAppUserNotFoundMessage(userId)))
        );
    }

    @Override
    @Log
    public Boolean checkEmailExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }

    @Override
    @Log
    public PaginatedResponseAppUserDto getAppUsersPaginated(Integer pageNumber, Integer pageSize, String sortBy) {
        Page<AppUser> appUsersPage = appUserRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));

        return PaginatedResponseAppUserDto.builder()
                .users(appUsersPage.getContent().stream()
                        .map(appUserConverter::convertModelToDto)
                        .toList())
                .numberOfItems(appUsersPage.getTotalElements())
                .numberOfPages(appUsersPage.getTotalPages())
                .build();
    }

    @Override
    @Log
    @Transactional
    public ResponseAppUserDto updateAppUser(Long appUserId, RequestAppUserDto requestAppUserDto) {
        validateEmailToUpdateAppUser(appUserId, requestAppUserDto.getEmail());

        Optional<AppUser> optionalAppUser = appUserRepository.findById(appUserId);

        optionalAppUser
                .orElseThrow(() -> new AppUserNotFoundException(getAppUserNotFoundMessage(appUserId)));

        optionalAppUser.ifPresent(updateAppUser -> {
            updateAppUser.setFirstName(requestAppUserDto.getFirstName());
            updateAppUser.setLastName(requestAppUserDto.getLastName());
            updateAppUser.setEmail(requestAppUserDto.getEmail());
            updateAppUser.setPhone(requestAppUserDto.getPhone());
            updateAppUser.setRole(RoleName.valueOf(requestAppUserDto.getRole()));
        });

        return appUserConverter.convertModelToDto(
                appUserRepository.findById(appUserId)
                        .orElseThrow(() -> new AppUserNotFoundException(getAppUserNotFoundMessage(appUserId)))
        );
    }

    private void validateEmailToUpdateAppUser(Long appUserId, String email) {
        Optional<AppUser> appUserByEmail = appUserRepository.findByEmail(email);
        if (appUserByEmail.isPresent() && !appUserByEmail.get().getId().equals(appUserId)) {
            throw new EmailAlreadyTakenException(getEmailAlreadyTakenMessage(email));
        }
    }
}
