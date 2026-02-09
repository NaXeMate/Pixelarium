package com.edu.mqt.pixelarium.model.dto.request;

/**
 * Represents a requested order item in create-order payloads.
 */
public record OrderItemDTORequest(
    Long productId,
    int quantity
) {}
