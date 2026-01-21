package com.edu.mqt.pixelarium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.mqt.pixelarium.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
