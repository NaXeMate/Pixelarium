package com.edu.mqt.pixelarium.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.edu.mqt.pixelarium.model.Order;
import com.edu.mqt.pixelarium.model.OrderItem;
import com.edu.mqt.pixelarium.model.Product;
import com.edu.mqt.pixelarium.model.User;
import com.edu.mqt.pixelarium.model.dto.response.OrderDTOResponse;
import com.edu.mqt.pixelarium.model.dto.response.OrderItemDTOResponse;
import com.edu.mqt.pixelarium.model.dto.response.ProductDTOResponse;
import com.edu.mqt.pixelarium.model.dto.response.UserDTOResponse;

public class EntityToDtoMapper {

    // Order --> OrderDTOResponse
    public static OrderDTOResponse toOrderDTO(Order order) {
        return new OrderDTOResponse(
            order.getId(),
            order.getUser().getId(),
            order.getOrderDate(),
            order.getTotalPrice(),
            order.getStatus(),
            EntityToDtoMapper.toOrderItemDTOList(order.getOrderItems())
        );
    }

    // OrderItem --> OrderItemDTOResponse
    public static OrderItemDTOResponse toOrderItemDTO(OrderItem item) {
        return new OrderItemDTOResponse(
            item.getId(),
            item.getProductId().getId(),
            item.getQuantity(),
            item.getUnitPrice()
        );
    }

    // User --> UserDTOResponse
    public static UserDTOResponse toUserDTO(User user) {
        return new UserDTOResponse(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getRegisterTime()
        );
    }

    // Product --> ProductDTOResponse
    public static ProductDTOResponse toProductDTO(Product product) {
        return new ProductDTOResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getSalePrice(),
            product.getImagePath(),
            product.getStock(),
            product.getCategory()
        );
    }

    public static List<OrderItemDTOResponse> toOrderItemDTOList(List<OrderItem> items) {
        return items.stream()
            .map(EntityToDtoMapper::toOrderItemDTO)
            .collect(Collectors.toList());
    }
}
