package com.edu.mqt.pixelarium.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.mqt.pixelarium.model.Product;
import com.edu.mqt.pixelarium.model.enumerated.Category;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String namePart);
    List<Product> findByPrice(BigDecimal price);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
    List<Product> findBySalePrice(BigDecimal salePrice);
    List<Product> findBySalePriceIsNotNull();
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryAndPrice(Category category, BigDecimal price);
    List<Product> findByCategoryAndPriceBetween(Category category, BigDecimal min, BigDecimal max);
}
