package org.example.graphQL;

import org.example.DTO.ProductDTO;
import org.example.services.ProductService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsMutation;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class ProductDataFetcher {

    private final ProductService productService;

    public ProductDataFetcher(ProductService productService) {
        this.productService = productService;
    }

    @DgsQuery
    public List<ProductDTO> allProducts() {
        return productService.getAllProducts()
                .stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getSize(), product.getStock(), product.getSalesCount()))
                .collect(Collectors.toList());
    }

    @DgsQuery
    public ProductDTO productById(Long id) {
        return productService.getProductById(id)
                .map(product -> new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getSize(), product.getStock(), product.getSalesCount()))
                .orElse(null);
    }

    @DgsMutation
    public ProductDTO createProduct(String name, String description, double price, String size, int stock, int salesCount) {
        ProductDTO productDTO = new ProductDTO(null, name, description, price, size, stock, salesCount);
        return productService.createProduct(productDTO);
    }

    @DgsMutation
    public ProductDTO updateProduct(Long id, String name, String description, double price, String size, int stock, int salesCount) {
        ProductDTO productDTO = new ProductDTO(id, name, description, price, size, stock, salesCount);
        return productService.updateProduct(id, productDTO);
    }

    @DgsMutation
    public boolean deleteProduct(Long id) {
        try {
            productService.deleteProduct(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
