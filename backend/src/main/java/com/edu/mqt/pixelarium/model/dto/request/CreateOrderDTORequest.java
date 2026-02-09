package com.edu.mqt.pixelarium.model.dto.request;

import java.util.List;

/**
 * Represents an order creation request payload.
 */
public record CreateOrderDTORequest(
    Long userId,
    List<OrderItemDTORequest> items
) {}
