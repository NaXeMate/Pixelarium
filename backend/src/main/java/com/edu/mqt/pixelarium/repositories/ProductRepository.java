package com.edu.mqt.pixelarium.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.mqt.pixelarium.model.Product;
import com.edu.mqt.pixelarium.model.enumerated.Category;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
           "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :namePart, '%'))")
    List<Product> findByNameContainingIgnoreCase(@Param("namePart") String namePart);
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
           "FROM Product p WHERE p.name = :name")
    boolean existsByName(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.price = :price")
    List<Product> findByPrice(@Param("price") BigDecimal price);

    @Query("SELECT p FROM Product p " +
           "WHERE p.price BETWEEN :min AND :max")
    List<Product> findByPriceBetween(@Param("min") BigDecimal min,
                                     @Param("max") BigDecimal max);

    @Query("SELECT p FROM Product p WHERE p.salePrice = :salePrice")
    List<Product> findBySalePrice(@Param("salePrice") BigDecimal salePrice);

    @Query("SELECT p FROM Product p WHERE p.salePrice IS NOT NULL")
    List<Product> findBySalePriceIsNotNull();

    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCategory(@Param("category") Category category);

    @Query("SELECT p FROM Product p " +
           "WHERE p.category = :category AND p.price = :price")
    List<Product> findByCategoryAndPrice(@Param("category") Category category,
                                         @Param("price") BigDecimal price);

    @Query("SELECT p FROM Product p " +
           "WHERE p.category = :category AND p.price BETWEEN :min AND :max")
    List<Product> findByCategoryAndPriceBetween(@Param("category") Category category,
                                                @Param("min") BigDecimal min,
                                                @Param("max") BigDecimal max);    
}