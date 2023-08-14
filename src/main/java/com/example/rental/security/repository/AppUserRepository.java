package com.example.rental.security.repository;

import com.example.rental.security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findUserByUsername(String username);
}
