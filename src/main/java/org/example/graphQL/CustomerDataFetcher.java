package org.example.graphQL;

import org.example.DTO.CustomerDTO;
import org.example.services.CustomerService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsMutation;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class CustomerDataFetcher {

    private final CustomerService customerService;

    public CustomerDataFetcher(CustomerService customerService) {
        this.customerService = customerService;
    }

    @DgsQuery
    public List<CustomerDTO> allCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customer -> new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(), customer.getAddress()))
                .collect(Collectors.toList());
    }

    @DgsQuery
    public CustomerDTO customerById(Long id) {
        return customerService.getCustomerById(id)
                .map(customer -> new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(), customer.getAddress()))
                .orElse(null);
    }

    @DgsMutation
    public CustomerDTO createCustomer(String name, String email, String address) {
        CustomerDTO customerDTO = new CustomerDTO(null, name, email, address);
        return customerService.createCustomer(customerDTO);
    }

    @DgsMutation
    public CustomerDTO updateCustomer(Long id, String name, String email, String address) {
        CustomerDTO customerDTO = new CustomerDTO(id, name, email, address);
        return customerService.updateCustomer(id, customerDTO);
    }

    @DgsMutation
    public boolean deleteCustomer(Long id) {
        try {
            customerService.deleteCustomer(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
