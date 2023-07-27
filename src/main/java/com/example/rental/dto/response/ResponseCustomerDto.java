package com.example.rental.dto.response;

import com.example.rental.dto.base.BaseDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ResponseCustomerDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
