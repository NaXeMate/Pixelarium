package com.edu.mqt.pixelarium.model.dto.request;

import java.math.BigDecimal;

import com.edu.mqt.pixelarium.model.enumerated.Category;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a product creation request payload.
 */
public record CreateProductDTORequest(
        @NotBlank(message = "Id is required.") Long id,

        @NotBlank(message = "Name is required.") String name,

        @NotBlank(message = "Description is required.") String description,

        @NotBlank(message = "Price is required.") BigDecimal price,

        BigDecimal salePrice,
        String imagePath,

        @NotBlank(message = "Stock is required.") Integer stock,

        @NotBlank(message = "Category is required.") Category category) {
}
