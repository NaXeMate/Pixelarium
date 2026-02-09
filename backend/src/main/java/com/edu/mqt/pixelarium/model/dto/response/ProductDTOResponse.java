package com.edu.mqt.pixelarium.model.dto.response;

import java.math.BigDecimal;

import com.edu.mqt.pixelarium.model.enumerated.Category;

/**
 * Represents a product in response payloads.
 */
public record ProductDTOResponse(
    Long id,
    String name,
    String description,
    BigDecimal price,
    BigDecimal salePrice,
    String imagePath,
    Integer stock,
    Category category
) {}
