package org.example.services.Impl;

import org.example.DTO.CustomerDTO;
import org.example.models.Customer;
import org.example.repository.CustomerRepository;
import org.example.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO updatedCustomerDTO) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    modelMapper.map(updatedCustomerDTO, existingCustomer);
                    Customer updatedCustomer = customerRepository.save(existingCustomer);
                    return modelMapper.map(updatedCustomer, CustomerDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));
    }

    @Override
    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Customer with id " + id + " not found");
        }
    }
}
