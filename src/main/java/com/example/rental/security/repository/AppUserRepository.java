package com.example.rental.security.repository;

import com.example.rental.security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findUserByUsername(String username);

    Optional<AppUser> findByEmail(String email);
}
