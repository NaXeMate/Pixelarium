package com.edu.mqt.pixelarium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.mqt.pixelarium.model.Order;
import com.edu.mqt.pixelarium.model.User;

import java.util.List;
import java.time.LocalDateTime;
import com.edu.mqt.pixelarium.model.vo.Status;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderDate(LocalDateTime orderDate);
    List<Order> findByTotalPrice(Double totalPrice);
    List<Order> findByStatus(Status status);
    List<Order> findByUserId(User userId);
}
