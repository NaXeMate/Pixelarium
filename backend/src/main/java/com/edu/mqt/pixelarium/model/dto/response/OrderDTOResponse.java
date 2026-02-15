package com.edu.mqt.pixelarium.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.edu.mqt.pixelarium.model.vo.Status;

/**
 * Represents an order in response payloads.
 */
public record OrderDTOResponse(
    Long id,
    Long userId,
    LocalDateTime orderDate,
    BigDecimal totalPrice,
    Status status,
    List<OrderItemDTOResponse> orderItems
) {}
