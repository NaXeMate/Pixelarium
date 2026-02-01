package com.edu.mqt.pixelarium.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.mqt.pixelarium.model.Product;
import com.edu.mqt.pixelarium.model.enumerated.Category;
import com.edu.mqt.pixelarium.repositories.ProductRepository;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    // ========= CRRUD =========

    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        Optional<Product> op = productRepo.findById(id);

        if (op.isPresent()) {
            return op.get();
        }

        return null;
    }

    public Product updateProduct(Product product) {
        if (productRepo.existsById(product.getId())) {
            System.out.println("Product updated in the database.");
            return productRepo.save(product);
        } else {
            System.out.println("An error has occurred and the database has not been updated.");
            return null;
        }
    }

    public void deleteProduct(Long id) {
        productRepo.findById(id).orElseThrow();
        productRepo.deleteById(id);
        System.out.println("Product successfully deleted from the database.");
    }

    // ========= CUSTOM METHODS =========

    @Transactional(readOnly = true)
    public List<Product> getByNameContainingIgnoreCase(String namePart) {
        return productRepo.findByNameContainingIgnoreCase(namePart);
    }

    @Transactional(readOnly = true)
    public List<Product> getByPrice(BigDecimal price) {
        return productRepo.findByPrice(price);
    }

    @Transactional(readOnly = true)
    public List<Product> getByPriceBetween(BigDecimal min, BigDecimal max) {
        return productRepo.findByPriceBetween(min, max);
    }

    @Transactional(readOnly = true)
    public List<Product> getBySalePrice(BigDecimal salePrice) {
        return productRepo.findBySalePrice(salePrice);
    }

    @Transactional(readOnly = true)
    public List<Product> getBySalePriceIsNotNull() {
        return productRepo.findBySalePriceIsNotNull();
    }

    @Transactional(readOnly = true)
    public List<Product> getByCategory(Category category) {
        return productRepo.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Product> getByCategoryAndPrice(Category category, BigDecimal price) {
        return productRepo.findByCategoryAndPrice(category, price);
    }

    @Transactional(readOnly = true)
    public List<Product> getByCategoryAndPriceBetween(Category category, BigDecimal min, BigDecimal max) {
        return productRepo.findByCategoryAndPriceBetween(category, min, max);
    }
}