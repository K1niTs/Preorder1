package org.example.controllers;

import org.example.DTO.ProductDTO;
import org.example.services.ProductService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController implements ProductApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        List<EntityModel<ProductDTO>> productModels = products.stream()
                .map(productDTO -> {
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProductById(productDTO.getId())).withSelfRel();
                    Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).updateProduct(productDTO.getId(), productDTO)).withRel("update");
                    Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).deleteProduct(productDTO.getId())).withRel("delete");
                    return EntityModel.of(productDTO, selfLink, updateLink, deleteLink);
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ProductDTO>> collectionModel = CollectionModel.of(productModels);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDTO>> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> product = productService.getProductById(id);
        if (product.isPresent()) {
            ProductDTO productDTO = product.get();
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProductById(id)).withSelfRel();
            Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).updateProduct(id, productDTO)).withRel("update");
            Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).deleteProduct(id)).withRel("delete");
            return ResponseEntity.ok(EntityModel.of(productDTO, selfLink, updateLink, deleteLink));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProductById(createdProduct.getId())).withSelfRel();
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(createdProduct, selfLink));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDTO>> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        if (updatedProduct != null) {
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProductById(updatedProduct.getId())).withSelfRel();
            Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).updateProduct(id, productDTO)).withRel("update");
            Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).deleteProduct(id)).withRel("delete");
            return ResponseEntity.ok(EntityModel.of(updatedProduct, selfLink, updateLink, deleteLink));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<ProductDTO> product = productService.getProductById(id);
        if (product.isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
