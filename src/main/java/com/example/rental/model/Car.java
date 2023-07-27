package com.example.rental.model;

import com.example.rental.enums.Status;
import com.example.rental.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Car extends BaseEntity<Long> {
    @Column
    private String make;

    @Column
    private String model;

    @Column
    private Integer year;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "rental_price_per_day")
    private Double rentalPricePerDay;

    @Enumerated(EnumType.STRING)
    private Status status;
}
