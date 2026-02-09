package com.edu.mqt.pixelarium.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.mqt.pixelarium.model.Product;
import com.edu.mqt.pixelarium.model.enumerated.Category;

import java.math.BigDecimal;

/**
 * Provides persistence operations for {@link Product} entities.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Finds products whose names contain the given fragment, ignoring case.
     *
     * @param namePart fragment to search for within product names
     * @return products whose names contain the provided fragment
     */
    @Query("SELECT p FROM Product p " +
           "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :namePart, '%'))")
    List<Product> findByNameContainingIgnoreCase(@Param("namePart") String namePart);
    /**
     * Checks whether a product exists with the exact given name.
     *
     * @param name product name to match
     * @return {@code true} if a product with the name exists; otherwise {@code false}
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
           "FROM Product p WHERE p.name = :name")
    boolean existsByName(@Param("name") String name);

    /**
     * Finds products with the exact price.
     *
     * @param price price to match
     * @return products priced exactly at the given value
     */
    @Query("SELECT p FROM Product p WHERE p.price = :price")
    List<Product> findByPrice(@Param("price") BigDecimal price);

    /**
     * Finds products with prices within the provided range.
     *
     * @param min minimum price (inclusive)
     * @param max maximum price (inclusive)
     * @return products with prices between the given bounds
     */
    @Query("SELECT p FROM Product p " +
           "WHERE p.price BETWEEN :min AND :max")
    List<Product> findByPriceBetween(@Param("min") BigDecimal min,
                                     @Param("max") BigDecimal max);

    /**
     * Finds products with the exact sale price.
     *
     * @param salePrice sale price to match
     * @return products with the given sale price
     */
    @Query("SELECT p FROM Product p WHERE p.salePrice = :salePrice")
    List<Product> findBySalePrice(@Param("salePrice") BigDecimal salePrice);

    /**
     * Finds products that currently have a sale price.
     *
     * @return products with non-null sale prices
     */
    @Query("SELECT p FROM Product p WHERE p.salePrice IS NOT NULL")
    List<Product> findBySalePriceIsNotNull();

    /**
     * Finds products that match the specified category.
     *
     * @param category category to match
     * @return products in the given category
     */
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCategory(@Param("category") Category category);

    /**
     * Finds products in a category with the exact price.
     *
     * @param category category to match
     * @param price price to match
     * @return products in the category with the given price
     */
    @Query("SELECT p FROM Product p " +
           "WHERE p.category = :category AND p.price = :price")
    List<Product> findByCategoryAndPrice(@Param("category") Category category,
                                         @Param("price") BigDecimal price);

    /**
     * Finds products in a category with prices within the provided range.
     *
     * @param category category to match
     * @param min minimum price (inclusive)
     * @param max maximum price (inclusive)
     * @return products in the category within the given price range
     */
    @Query("SELECT p FROM Product p " +
           "WHERE p.category = :category AND p.price BETWEEN :min AND :max")
    List<Product> findByCategoryAndPriceBetween(@Param("category") Category category,
                                                @Param("min") BigDecimal min,
                                                @Param("max") BigDecimal max);    
}
