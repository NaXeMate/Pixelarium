package com.edu.mqt.pixelarium.repositories;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.mqt.pixelarium.model.Product;
import java.math.BigDecimal;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPrice(BigDecimal price);
    List<Product> findBySalePrice(BigDecimal salePrice);
    List<Category> findByCategory(Category category);

}
