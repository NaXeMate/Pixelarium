package com.edu.mqt.pixelarium.model.dto.request;

import java.util.List;

public record CreateOrderRequestDTO(
    Long userId,
    List<OrderItemRequestDTO> items
) {}
