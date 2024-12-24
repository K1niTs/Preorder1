package org.example.repository;


import org.example.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    @Query("SELECT p FROM Product p ORDER BY p.salesCount DESC")
    List<Product> findTopSellingProducts();
}
