package com.example.rental.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCustomerDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
