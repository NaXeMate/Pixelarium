package com.edu.mqt.pixelarium.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.edu.mqt.pixelarium.model.vo.Status;

public record OrderDTOResponse(
    Long id,
    Long userId,
    LocalDateTime orderDate,
    Double totalPrice,
    Status status,
    List<OrderItemDTOResponse> orderItems
) {}
