package org.example;

import org.example.DTO.enums.OrderStatusType;
import org.example.models.*;
import org.example.repository.CustomerRepository;
import org.example.repository.OrderStatusRepository;
import org.example.repository.PreOrderRepository;
import org.example.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PreOrderRepository preOrderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderStatusRepository orderStatusRepository;

    public DataInitializer(PreOrderRepository preOrderRepository, ProductRepository productRepository,
                           CustomerRepository customerRepository, OrderStatusRepository orderStatusRepository) {
        this.preOrderRepository = preOrderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("Product 1 description");
        product1.setPrice(100.00);
        product1.setSize("S");
        product1.setStock(10);
        product1.setSalesCount(100);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setDescription("Product 2 description");
        product2.setPrice(0);
        product2.setSize("M");
        product2.setStock(15);
        product2.setSalesCount(80);
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Product 3");
        product3.setDescription("Product 3 description");
        product3.setSize("M");
        product3.setStock(15);
        product3.setSalesCount(80);
        productRepository.save(product3);
        OrderStatus pendingStatus = new OrderStatus();
        pendingStatus.setStatus(OrderStatusType.valueOf("PENDING"));
        orderStatusRepository.save(pendingStatus);

        OrderStatus confirmedStatus = new OrderStatus();
        confirmedStatus.setStatus(OrderStatusType.valueOf("SHIPPED"));
        orderStatusRepository.save(confirmedStatus);

        Customer customer1 = new Customer();
        customer1.setName("John Doe");
        customer1.setEmail("johndoe@example.com");
        customer1.setAddress("123 Elm Street");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Jane Smith");
        customer2.setEmail("janesmith@example.com");
        customer2.setAddress("456 Oak Avenue");
        customerRepository.save(customer2);

        PreOrder preOrder1 = new PreOrder();
        preOrder1.setProduct(product1);
        preOrder1.setQuantity(2);
        preOrder1.setCustomer(customer1);
        preOrder1.setStatus(pendingStatus);
        preOrderRepository.save(preOrder1);

        PreOrder preOrder2 = new PreOrder();
        preOrder2.setProduct(product2);
        preOrder2.setQuantity(1);
        preOrder2.setCustomer(customer2);
        preOrder2.setStatus(confirmedStatus);
        preOrderRepository.save(preOrder2);
    }
}
