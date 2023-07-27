package com.example.rental.model;

import com.example.rental.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Category extends BaseEntity<Long> {

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Car> cars;

    public void addCar(Car car) {
        cars.add(car);
    }
}

