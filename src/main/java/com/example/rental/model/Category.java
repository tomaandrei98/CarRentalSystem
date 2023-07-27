package com.example.rental.model;

import com.example.rental.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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
}

