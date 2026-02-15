package com.edu.mqt.pixelarium.model.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents an order creation request payload.
 */
public record CreateOrderDTORequest(
        @NotBlank(message = "User id is required.") Long userId,
        @NotBlank(message = "At least, one item is required to create an order.") List<OrderItemDTORequest> items) {
}
