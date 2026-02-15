package com.edu.mqt.pixelarium.model.dto.response;

import java.math.BigDecimal;

/**
 * Represents an order item in response payloads.
 */
public record OrderItemDTOResponse(
    Long id,
    Long productId,
    int quantity,
    BigDecimal unitPrice
) {}
