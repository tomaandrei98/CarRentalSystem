package com.example.rental.dto.base;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseDto implements Serializable {
    private Long id;
}
