package com.example.rental.service;

import com.example.rental.dto.request.RequestAppUserDto;
import com.example.rental.dto.response.ResponseAppUserDto;
import com.example.rental.dto.response.paginated.PaginatedResponseAppUserDto;

import java.util.List;

public interface AppUserService {
    List<ResponseAppUserDto> getAllUsers();

    ResponseAppUserDto findUserByUsername(String username);

    void deleteUserByUsername(String username);

    ResponseAppUserDto getUserById(Long userId);

    Boolean checkEmailExists(String email);

    PaginatedResponseAppUserDto getAppUsersPaginated(Integer pageNumber, Integer pageSize, String sortBy);

    ResponseAppUserDto updateAppUser(Long appUserId, RequestAppUserDto requestAppUserDto);

    // todo: add get logged in user to display the user info page
}
