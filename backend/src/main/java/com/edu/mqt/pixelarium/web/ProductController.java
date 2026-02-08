package com.edu.mqt.pixelarium.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.mqt.pixelarium.model.Product;
import com.edu.mqt.pixelarium.model.dto.request.CreateProductDTORequest;
import com.edu.mqt.pixelarium.model.enumerated.Category;
import com.edu.mqt.pixelarium.service.ProductService;

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

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct (@RequestBody CreateProductDTORequest productDTO) {
        Product createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product productDetails) {
        productDetails.setId(id);
        Product updatedProduct = productService.updateProduct(productDetails);
        return ResponseEntity.ok(updatedProduct);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable Category category) {
        List<Product> products = productService.getByCategory(category);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/price-range/{min}-{max}")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@PathVariable BigDecimal min, BigDecimal max) {
        List<Product> products = productService.getByPriceBetween(min, max);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sale-offers")
    public ResponseEntity<List<Product>> getProductsOnSale() {
        List<Product> productsOnSale = productService.getBySalePriceIsNotNull();
        return ResponseEntity.ok(productsOnSale);
    }
}
