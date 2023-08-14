package com.example.rental.utils.converter;

import com.example.rental.dto.response.ResponseAppUserDto;
import com.example.rental.model.base.BaseEntity;
import com.example.rental.security.model.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserConverter {
    public ResponseAppUserDto convertModelToDto(AppUser appUser) {
        ResponseAppUserDto appUserDto = ResponseAppUserDto.builder()
                .username(appUser.getUsername())
                .email(appUser.getEmail())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .phone(appUser.getPhone())
                .role(appUser.getRole().name())
                .rentals(appUser.getRentals().stream().map(BaseEntity::getId).toList())
                .build();
        
        appUserDto.setId(appUser.getId());
        return appUserDto;
    }
}
