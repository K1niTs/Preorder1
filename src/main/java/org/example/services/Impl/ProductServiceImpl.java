package org.example.services.Impl;

import org.example.DTO.ProductDTO;
import org.example.models.PreOrder;
import org.example.models.Product;
import org.example.repository.PreOrderRepository;
import org.example.repository.ProductRepository;
import org.example.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final PreOrderRepository preOrderRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, PreOrderRepository preOrderRepository) {
        this.productRepository = productRepository;
        this.preOrderRepository = preOrderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductDTO.class));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO updatedProductDTO) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    modelMapper.map(updatedProductDTO, existingProduct);
                    Product updatedProduct = productRepository.save(existingProduct);
                    return modelMapper.map(updatedProduct, ProductDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " not found"));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " not found"));

        List<PreOrder> preOrders = preOrderRepository.findByProductId(id);
        if (!preOrders.isEmpty()) {
            preOrderRepository.deleteAll(preOrders);
        }

        productRepository.delete(product);
    }

}
