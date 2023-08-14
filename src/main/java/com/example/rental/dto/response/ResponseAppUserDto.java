package com.example.rental.dto.response;

import com.example.rental.dto.base.BaseDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ResponseAppUserDto extends BaseDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
    private List<Long> rentals;
}
