package com.example.rental.dto.response;

import com.example.rental.dto.base.BaseDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ResponseCategoryDto extends BaseDto {
    private String name;
    private String description;
}
