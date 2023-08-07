package com.example.rental.repository;

import com.example.rental.model.AppUser;
import com.example.rental.repository.base.ShopRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends ShopRepository<AppUser, Long> {
    AppUser findAppUserByUsername(String username);
}
