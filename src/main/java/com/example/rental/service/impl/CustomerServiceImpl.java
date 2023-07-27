package com.example.rental.service.impl;

import com.example.rental.dto.request.RequestCustomerDto;
import com.example.rental.dto.response.ResponseCustomerDto;
import com.example.rental.exception.CustomerNotFoundException;
import com.example.rental.model.Customer;
import com.example.rental.repository.CustomerRepository;
import com.example.rental.service.CustomerService;
import com.example.rental.utils.converter.CustomerConverter;
import com.example.rental.utils.logger.Log;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.rental.utils.MessageGenerator.getCustomerNotFoundMessage;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    @Override
    @Log
    public List<ResponseCustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerConverter::convertModelToResponseDto)
                .toList();
    }

    @Override
    @Log
    public ResponseCustomerDto getCustomerById(Long customerId) {
        Customer downloadedCustomer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new CustomerNotFoundException(getCustomerNotFoundMessage(customerId))
                );

        return customerConverter.convertModelToResponseDto(downloadedCustomer);
    }

    @Override
    @Log
    public ResponseCustomerDto saveCustomer(RequestCustomerDto requestCustomerDto) {
        Customer customerToSave = customerConverter.convertRequestToModel(requestCustomerDto);
        Customer savedCustomer = customerRepository.save(customerToSave);
        return customerConverter.convertModelToResponseDto(savedCustomer);
    }

    @Override
    @Log
    @Transactional
    public ResponseCustomerDto updateCustomer(Long customerId, RequestCustomerDto requestCustomerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        optionalCustomer
                .orElseThrow(
                        () -> new CustomerNotFoundException(getCustomerNotFoundMessage(customerId))
                );

        optionalCustomer.ifPresent(updateCustomer -> {
            updateCustomer.setFirstName(requestCustomerDto.getFirstName());
            updateCustomer.setLastName(requestCustomerDto.getLastName());
            updateCustomer.setEmail(requestCustomerDto.getEmail());
            updateCustomer.setPhone(requestCustomerDto.getPhone());
        });

        return customerConverter.convertModelToResponseDto(
                customerRepository.findById(customerId)
                        .orElseThrow(
                                () -> new CustomerNotFoundException(getCustomerNotFoundMessage(customerId))
                        )
        );
    }

    @Override
    @Log
    public void deleteCustomerById(Long customerId) {
        Customer customerToDelete = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new CustomerNotFoundException(getCustomerNotFoundMessage(customerId))
                );

        customerRepository.delete(customerToDelete);
    }
}
