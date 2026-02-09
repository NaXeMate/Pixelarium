package com.edu.mqt.pixelarium.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.edu.mqt.pixelarium.model.enumerated.Category;

import jakarta.persistence.*;

/**
 * Represents a catalog product and its pricing details.
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    @Column(nullable = false, unique = true, length = 200)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = true, name = "sale_price")
    private BigDecimal salePrice;

    @Column(nullable = true, length = 255, name = "image_path")
    private String imagePath;

    @Column(nullable = false)
    private Integer stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    /**
     * Creates an empty product for JPA.
     */
    public Product() {}

    /**
     * Creates a product with the provided attributes.
     *
     * @param id product identifier
     * @param name product name
     * @param description product description
     * @param price regular price
     * @param salePrice optional sale price
     * @param imagePath image path or URL
     * @param stock available stock quantity
     * @param category product category
     */
    public Product(Long id, String name, String description, BigDecimal price, BigDecimal salePrice, String imagePath,
            Integer stock, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.salePrice = salePrice;
        this.imagePath = imagePath;
        this.stock = stock;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
