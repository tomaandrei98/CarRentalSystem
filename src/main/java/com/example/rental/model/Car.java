package com.example.rental.model;

import com.example.rental.enums.Status;
import com.example.rental.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
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

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
