package com.example.rental.service;

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

    // todo: add update app user and get logged in user
}
