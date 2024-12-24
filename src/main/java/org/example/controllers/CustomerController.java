package org.example.controllers;

import org.example.DTO.CustomerDTO;
import org.example.services.CustomerService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        List<EntityModel<CustomerDTO>> customerModels = customers.stream()
                .map(customerDTO -> {
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(customerDTO.getId())).withSelfRel();
                    Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).updateCustomer(customerDTO.getId(), customerDTO)).withRel("update");
                    Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).deleteCustomer(customerDTO.getId())).withRel("delete");
                    return EntityModel.of(customerDTO, selfLink, updateLink, deleteLink);
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CustomerDTO>> collectionModel = CollectionModel.of(customerModels);
        return ResponseEntity.ok(collectionModel);
    }

    @Override
    public ResponseEntity<EntityModel<CustomerDTO>> getCustomerById(@PathVariable Long id) {
        Optional<CustomerDTO> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            CustomerDTO customerDTO = customer.get();
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(id)).withSelfRel();
            Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).updateCustomer(id, customerDTO)).withRel("update");
            Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).deleteCustomer(id)).withRel("delete");
            return ResponseEntity.ok(EntityModel.of(customerDTO, selfLink, updateLink, deleteLink));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<EntityModel<CustomerDTO>> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(createdCustomer.getId())).withSelfRel();
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(createdCustomer, selfLink));
    }

    @Override
    public ResponseEntity<EntityModel<CustomerDTO>> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        if (updatedCustomer != null) {
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(updatedCustomer.getId())).withSelfRel();
            Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).updateCustomer(id, customerDTO)).withRel("update");
            Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).deleteCustomer(id)).withRel("delete");
            return ResponseEntity.ok(EntityModel.of(updatedCustomer, selfLink, updateLink, deleteLink));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<CustomerDTO> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
