package com.edu.mqt.pixelarium.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.mqt.pixelarium.model.dto.request.CreateProductDTORequest;
import com.edu.mqt.pixelarium.model.entities.Product;
import com.edu.mqt.pixelarium.model.enumerated.Category;
import com.edu.mqt.pixelarium.service.ProductService;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Exposes product-related endpoints under {@code /api/products}.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Creates a controller backed by the given product service.
     *
     * @param productService service used to handle product operations
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Returns all products.
     *
     * @return the list of products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Returns a product by its identifier.
     *
     * @param id product identifier
     * @return the product with the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Creates a new product from the provided payload.
     *
     * @param productDTO request payload with product attributes
     * @return the created product
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductDTORequest productDTO) {
        Product createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Updates an existing product using the provided details.
     *
     * @param id             product identifier to update
     * @param productDetails updated product data
     * @return the updated product
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product productDetails) {
        productDetails.setId(id);
        Product updatedProduct = productService.updateProduct(productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Deletes a product by its identifier.
     *
     * @param id product identifier
     * @return an empty response with {@code 204 No Content}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns all products that match the given category.
     *
     * @param category product category to filter by
     * @return the list of products in the requested category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable Category category) {
        List<Product> products = productService.getByCategory(category);
        return ResponseEntity.ok(products);
    }

    /**
     * Returns products whose price falls within the given range.
     *
     * @param min minimum price (inclusive)
     * @param max maximum price (inclusive)
     * @return the list of products within the price range
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<Product> products = productService.getByPriceBetween(min, max);
        return ResponseEntity.ok(products);
    }

    /**
     * Returns products that have a non-null sale price.
     *
     * @return the list of products currently on sale
     */
    @GetMapping("/sale-offers")
    public ResponseEntity<List<Product>> getProductsOnSale() {
        List<Product> productsOnSale = productService.getBySalePriceIsNotNull();
        return ResponseEntity.ok(productsOnSale);
    }
}
