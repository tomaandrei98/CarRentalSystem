package com.example.rental.dto.request;

import com.example.rental.dto.base.BaseDto;
import com.example.rental.enums.Status;
import jakarta.persistence.Column;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class RequestCustomerDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
