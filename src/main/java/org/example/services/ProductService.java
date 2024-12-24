package org.example.services;


import org.example.DTO.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    Optional<ProductDTO> getProductById(Long id);
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(Long id, ProductDTO updatedProductDTO);
    void deleteProduct(Long id);
}
