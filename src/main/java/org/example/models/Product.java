package org.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private double price;
    private String size;
    private int stock;
    private int salesCount;

    public Product() {}

    public Product(Long id, String name, String description, double price, String size, int stock, int salesCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.size = size;
        this.stock = stock;
        this.salesCount = salesCount;

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getSalesCount() { return salesCount; }
    public void setSalesCount(int salesCount) { this.salesCount = salesCount; }
    }

