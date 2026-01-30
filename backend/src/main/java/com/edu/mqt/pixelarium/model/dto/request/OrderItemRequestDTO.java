package com.edu.mqt.pixelarium.model.dto.request;

public record OrderItemRequestDTO(
    Long productId,
    int quantity
) {}
