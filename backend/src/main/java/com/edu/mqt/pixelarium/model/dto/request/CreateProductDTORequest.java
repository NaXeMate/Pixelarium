package com.edu.mqt.pixelarium.model.dto.request;

import java.math.BigDecimal;

import com.edu.mqt.pixelarium.model.enumerated.Category;

public record CreateProductDTORequest(
    Long id,
    String name,
    String description,
    BigDecimal price,
    BigDecimal salePrice,
    String imagePath,
    Integer stock,
    Category category
) {}
