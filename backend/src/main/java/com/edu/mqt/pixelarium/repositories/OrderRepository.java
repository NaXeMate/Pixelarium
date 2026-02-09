package com.edu.mqt.pixelarium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.mqt.pixelarium.model.Order;

import java.util.List;
import java.time.LocalDateTime;
import com.edu.mqt.pixelarium.model.vo.Status;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.orderDate = :orderDate")
    List<Order> findByOrderDate(@Param("orderDate") LocalDateTime orderDate);
    @Query("SELECT o FROM Order o WHERE o.totalPrice = :totalPrice")
    List<Order> findByTotalPrice(@Param("totalPrice") Double totalPrice);
    @Query("SELECT o FROM Order o WHERE o.status = :status")
    List<Order> findByStatus(@Param("status") Status status);
    @Query("SELECT o FROM Order o WHERE o.user.id = :user")
    List<Order> findByUserId(@Param("user") Long user);
}
