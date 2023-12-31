package com.example.rental.dto.response;

import com.example.rental.dto.base.BaseDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ResponseCategoryDto extends BaseDto {
    private String name;
    private String description;
    private List<Long> carsId;
}
