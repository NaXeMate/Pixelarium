package com.edu.mqt.pixelarium.model.dto.response;

import java.math.BigDecimal;

public record OrderItemDTOResponse(
    Long id,
    Long productId,
    int quantity,
    BigDecimal unitPrice
) {}
