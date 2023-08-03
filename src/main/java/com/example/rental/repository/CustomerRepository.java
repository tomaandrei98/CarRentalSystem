package com.example.rental.repository;

import com.example.rental.model.Customer;
import com.example.rental.repository.base.ShopRepository;

import java.util.Optional;

public interface CustomerRepository extends ShopRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
