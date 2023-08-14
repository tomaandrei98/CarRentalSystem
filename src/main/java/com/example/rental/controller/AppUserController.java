package com.example.rental.controller;

import com.example.rental.dto.response.ResponseAppUserDto;
import com.example.rental.dto.response.general.ApiResponse;
import com.example.rental.dto.response.paginated.PaginatedResponseAppUserDto;
import com.example.rental.dto.response.paginated.PaginatedResponseCustomerDto;
import com.example.rental.service.AppUserService;
import com.example.rental.utils.logger.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    @Log
    public ResponseEntity<ApiResponse<List<ResponseAppUserDto>>> getAllUsers() {
        return ResponseEntity.ok(
                new ApiResponse<>(appUserService.getAllUsers(), "Users downloaded successfully.")
        );
    }

    @GetMapping("/{username}")
    @Log
    public ResponseEntity<ApiResponse<ResponseAppUserDto>> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(
                new ApiResponse<>(appUserService.findUserByUsername(username), "User downloaded successfully.")
        );
    }

    @GetMapping("/id/{id}")
    @Log
    public ResponseEntity<ApiResponse<ResponseAppUserDto>> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(appUserService.getUserById(id), "User downloaded successfully.")
        );
    }

    @GetMapping("/paginated")
    @Log
    public ResponseEntity<ApiResponse<PaginatedResponseAppUserDto>> getAppUsersPaginated(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "firstName") String sortBy
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(appUserService.getAppUsersPaginated(pageNumber, pageSize, sortBy),
                        "Users downloaded successfully.")
        );
    }

    @DeleteMapping("/{username}")
    @Log
    public ResponseEntity<ApiResponse<Void>> deleteUserByUsername(@PathVariable String username) {
        appUserService.deleteUserByUsername(username);

        return ResponseEntity.ok(
                new ApiResponse<>("User deleted successfully")
        );
    }

    @GetMapping("/check-email")
    @Log
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam("email") String email) {
        return ResponseEntity.ok(appUserService.checkEmailExists(email));
    }

}
