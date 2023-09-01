package com.example.rental.utils.converter;

import com.example.rental.dto.response.ResponseAppUserDto;
import com.example.rental.security.model.AppUser;
import com.example.rental.security.model.RoleName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AppUserConverterTest {
    @Test
    public void testConvertModelToDto() {
        AppUser appUser = AppUser.builder()
                .username("john_doe")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .phone("1234567890")
                .role(RoleName.USER)
                .rentals(new ArrayList<>())
                .build();
        appUser.setId(1L);

        ResponseAppUserDto responseDto = new AppUserConverter().convertModelToDto(appUser);

        assertEquals(appUser.getId(), responseDto.getId());
        assertEquals(appUser.getUsername(), responseDto.getUsername());
        assertEquals(appUser.getEmail(), responseDto.getEmail());
        assertEquals(appUser.getFirstName(), responseDto.getFirstName());
        assertEquals(appUser.getLastName(), responseDto.getLastName());
        assertEquals(appUser.getPhone(), responseDto.getPhone());
        assertEquals(appUser.getRole().name(), responseDto.getRole());
    }
}