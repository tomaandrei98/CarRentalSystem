package com.example.rental.repository;

import com.example.rental.model.Car;
import com.example.rental.repository.base.ShopRepository;

import java.util.List;

public interface CarRepository extends ShopRepository<Car, Long> {
}
