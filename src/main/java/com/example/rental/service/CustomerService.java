package com.example.rental.service;

import com.example.rental.dto.request.RequestCustomerDto;
import com.example.rental.dto.response.ResponseCustomerDto;

import java.util.List;

public interface CustomerService {
    List<ResponseCustomerDto> getAllCustomers();

    ResponseCustomerDto getCustomerById(Long customerId);

    ResponseCustomerDto saveCustomer(RequestCustomerDto requestCustomerDto);

    ResponseCustomerDto updateCustomer(Long customerId, RequestCustomerDto requestCustomerDto);

    void deleteCustomerById(Long customerId);
}
