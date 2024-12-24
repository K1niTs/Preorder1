package org.example.services;


import org.example.DTO.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    Optional<CustomerDTO> getCustomerById(Long id);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO updateCustomer(Long id, CustomerDTO updatedCustomerDTO);
    void deleteCustomer(Long id);
}
