package com.edu.mqt.pixelarium.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.edu.mqt.pixelarium.exception.ResourceNotFoundException;
import com.edu.mqt.pixelarium.mapper.EntityToDtoMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.mqt.pixelarium.model.dto.request.CreateProductDTORequest;
import com.edu.mqt.pixelarium.model.dto.response.ProductDTOResponse;
import com.edu.mqt.pixelarium.model.entities.Product;
import com.edu.mqt.pixelarium.model.enumerated.Category;
import com.edu.mqt.pixelarium.repositories.ProductRepository;

/**
 * Provides product-related business operations.
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepo;

    /**
     * Creates a service backed by the given repository.
     *
     * @param productRepo repository used to persist products
     */
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    // ========= CRRUD =========

    /**
     * Returns all products.
     *
     * @return the list of products
     */
    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    /**
     * Returns a product by id, or {@code null} if it does not exist.
     *
     * @param id product identifier
     * @return the product if found, otherwise {@code null}
     */
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    /**
     * Updates an existing product if it already exists.
     *
     * @param product product data to persist
     * @return the saved product, or {@code null} if the product does not exist
     */
    public Product updateProduct(Product product) {
        if (!productRepo.existsById(product.getId())) {
            throw new ResourceNotFoundException("Product not found with id: " + product.getId());
        }
        return productRepo.save(product);
    }

    /**
     * Deletes a product by id.
     *
     * @param id product identifier
     * @throws java.util.NoSuchElementException if the product does not exist
     */
    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepo.deleteById(id);
    }

    // ========= CUSTOM METHODS =========

    /**
     * Creates a new product after validating uniqueness and pricing rules.
     *
     * @param productDTO request payload with product data
     * @return the created product
     * @throws IllegalArgumentException if the product name already exists
     * @throws IllegalArgumentException if the price is negative
     * @throws IllegalArgumentException if the stock is negative
     * @throws IllegalArgumentException if the sale price is invalid
     */
    public Product createProduct(CreateProductDTORequest productDTO) {
        String name = productDTO.name();
        if (productRepo.existsByName(name)) {
            throw new IllegalArgumentException("Product already exists with name: " + name);
        }

        if (productDTO.price().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }

        if (productDTO.stock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative!");
        }

        if (productDTO.salePrice() != null) {
            if (productDTO.salePrice().compareTo(productDTO.price()) >= 0) {
                throw new IllegalArgumentException("Sale price must be lower than regular price!");
            }
            if (productDTO.salePrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Sale price cannot be negative!");
            }
        }

        Product newProduct = new Product();

        newProduct.setName(productDTO.name());
        newProduct.setDescription(productDTO.description());
        newProduct.setPrice(productDTO.price());
        newProduct.setSalePrice(productDTO.salePrice());
        newProduct.setImagePath(productDTO.imagePath());
        newProduct.setStock(productDTO.stock());
        newProduct.setCategory(productDTO.category());

        return productRepo.save(newProduct);
    }

    /**
     * Returns products whose names contain the given fragment, ignoring case.
     *
     * @param namePart fragment to search for
     * @return matching products
     */
    @Transactional(readOnly = true)
    public List<Product> getByNameContainingIgnoreCase(String namePart) {
        return productRepo.findByNameContainingIgnoreCase(namePart);
    }

    /**
     * Searches for products by name or description, ignoring case.
     * 
     * @param query search query
     * @return matching products
     */
    @Transactional(readOnly = true)
    public List<ProductDTOResponse> searchProducts(String query) {
        return productRepo.searchByNameOrDescription(query)
                .stream()
                .map(EntityToDtoMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    /**
     * Returns products with the exact price.
     *
     * @param price price to match
     * @return matching products
     */
    @Transactional(readOnly = true)
    public List<Product> getByPrice(BigDecimal price) {
        return productRepo.findByPrice(price);
    }

    /**
     * Returns products within the given price range.
     *
     * @param min minimum price (inclusive)
     * @param max maximum price (inclusive)
     * @return matching products
     */
    @Transactional(readOnly = true)
    public List<Product> getByPriceBetween(BigDecimal min, BigDecimal max) {
        return productRepo.findByPriceBetween(min, max);
    }

    /**
     * Returns products with the exact sale price.
     *
     * @param salePrice sale price to match
     * @return matching products
     */
    @Transactional(readOnly = true)
    public List<Product> getBySalePrice(BigDecimal salePrice) {
        return productRepo.findBySalePrice(salePrice);
    }

    /**
     * Returns products that have a sale price.
     *
     * @return products with non-null sale prices
     */
    @Transactional(readOnly = true)
    public List<Product> getBySalePriceIsNotNull() {
        return productRepo.findBySalePriceIsNotNull();
    }

    /**
     * Returns products in the given category.
     *
     * @param category category to match
     * @return matching products
     */
    @Transactional(readOnly = true)
    public List<Product> getByCategory(Category category) {
        return productRepo.findByCategory(category);
    }

    /**
     * Returns products in a category with the exact price.
     *
     * @param category category to match
     * @param price    price to match
     * @return matching products
     */
    @Transactional(readOnly = true)
    public List<Product> getByCategoryAndPrice(Category category, BigDecimal price) {
        return productRepo.findByCategoryAndPrice(category, price);
    }

    /**
     * Returns products in a category within the given price range.
     *
     * @param category category to match
     * @param min      minimum price (inclusive)
     * @param max      maximum price (inclusive)
     * @return matching products
     */
    @Transactional(readOnly = true)
    public List<Product> getByCategoryAndPriceBetween(Category category, BigDecimal min, BigDecimal max) {
        return productRepo.findByCategoryAndPriceBetween(category, min, max);
    }
}
