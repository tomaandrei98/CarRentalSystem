package com.example.rental.utils.converter;

import com.example.rental.dto.request.RequestCustomerDto;
import com.example.rental.dto.response.ResponseCustomerDto;
import com.example.rental.model.Customer;
import com.example.rental.model.base.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomerConverter {
    public ResponseCustomerDto convertModelToResponseDto(Customer customer) {
        ResponseCustomerDto response = ResponseCustomerDto.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
//                .rentalsId(customer.getRentals().stream().map(BaseEntity::getId).toList())
                .build();
        response.setId(customer.getId());
        return response;
    }

    public Customer convertRequestToModel(RequestCustomerDto requestCustomerDto) {
        return Customer.builder()
                .firstName(requestCustomerDto.getFirstName())
                .lastName(requestCustomerDto.getLastName())
                .email(requestCustomerDto.getEmail())
                .phone(requestCustomerDto.getPhone())
//                .rentals(new ArrayList<>())
                .build();
    }
}
