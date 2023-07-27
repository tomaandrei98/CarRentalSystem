package com.example.rental.dto.request;

import com.example.rental.dto.base.BaseDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class RequestCategoryDto extends BaseDto {
    private String name;
    private String description;
}