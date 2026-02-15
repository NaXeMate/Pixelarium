package com.edu.mqt.pixelarium.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.edu.mqt.pixelarium.model.dto.response.OrderDTOResponse;
import com.edu.mqt.pixelarium.model.dto.response.OrderItemDTOResponse;
import com.edu.mqt.pixelarium.model.dto.response.ProductDTOResponse;
import com.edu.mqt.pixelarium.model.dto.response.UserDTOResponse;
import com.edu.mqt.pixelarium.model.entities.Order;
import com.edu.mqt.pixelarium.model.entities.OrderItem;
import com.edu.mqt.pixelarium.model.entities.Product;
import com.edu.mqt.pixelarium.model.entities.User;

/**
 * Maps domain entities to response DTOs.
 */
public class EntityToDtoMapper {

    // Order --> OrderDTOResponse
    /**
     * Creates a DTO representation of the given order.
     *
     * @param order source order entity
     * @return mapped order DTO
     */
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
    /**
     * Creates a DTO representation of the given order item.
     *
     * @param item source order item entity
     * @return mapped order item DTO
     */
    public static OrderItemDTOResponse toOrderItemDTO(OrderItem item) {
        return new OrderItemDTOResponse(
            item.getId(),
            item.getProductId().getId(),
            item.getQuantity(),
            item.getUnitPrice()
        );
    }

    // User --> UserDTOResponse
    /**
     * Creates a DTO representation of the given user.
     *
     * @param user source user entity
     * @return mapped user DTO
     */
    public static UserDTOResponse toUserDTO(User user) {
        return new UserDTOResponse(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getRegisterTime()
        );
    }

    // Product --> ProductDTOResponse
    /**
     * Creates a DTO representation of the given product.
     *
     * @param product source product entity
     * @return mapped product DTO
     */
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

    /**
     * Creates a list of DTOs from a list of order items.
     *
     * @param items source order items
     * @return mapped list of order item DTOs
     */
    public static List<OrderItemDTOResponse> toOrderItemDTOList(List<OrderItem> items) {
        return items.stream()
            .map(EntityToDtoMapper::toOrderItemDTO)
            .collect(Collectors.toList());
    }
}
