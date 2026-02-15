package com.edu.mqt.pixelarium.model.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a requested order item in create-order payloads.
 */
public record OrderItemDTORequest(
        @NotBlank(message = "You must specify the product from the list.") Long productId,

        @NotBlank(message = "You must specify how many items you want to order.") int quantity) {
}
