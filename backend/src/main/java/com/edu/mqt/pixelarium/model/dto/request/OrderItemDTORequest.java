package com.edu.mqt.pixelarium.model.dto.request;

public record OrderItemDTORequest(
    Long productId,
    int quantity
) {}
