package com.edu.mqt.pixelarium.model.dto.request;

import java.util.List;

public record CreateOrderDTORequest(
    Long userId,
    List<OrderItemDTORequest> items
) {}
