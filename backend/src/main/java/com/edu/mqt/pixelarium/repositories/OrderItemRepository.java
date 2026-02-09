package com.edu.mqt.pixelarium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.mqt.pixelarium.model.OrderItem;

/**
 * Provides persistence operations for {@link OrderItem} entities.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
