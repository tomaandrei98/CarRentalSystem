package com.example.rental.security.model;

import com.example.rental.model.Rental;
import com.example.rental.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class AppUser extends BaseEntity<Long> {

    @Column(unique = true)
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private RoleName role;

    @OneToMany(mappedBy = "appUser")
    private List<Rental> rentals;

    public void addRental(Rental rental) {
        rentals.add(rental);
    }
}