package com.edu.mqt.pixelarium.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.mqt.pixelarium.model.Order;

import java.util.List;
import java.time.LocalDateTime;
import com.edu.mqt.pixelarium.model.vo.Status;

/**
 * Provides persistence operations for {@link Order} entities.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Finds orders with the exact order date-time.
     *
     * @param orderDate order date-time to match
     * @return orders placed at the given date-time
     */
    @Query("SELECT o FROM Order o WHERE o.orderDate = :orderDate")
    List<Order> findByOrderDate(@Param("orderDate") LocalDateTime orderDate);
    /**
     * Finds orders with the exact total price.
     *
     * @param totalPrice total price to match
     * @return orders whose total price equals the provided value
     */
    @Query("SELECT o FROM Order o WHERE o.totalPrice = :totalPrice")
    List<Order> findByTotalPrice(@Param("totalPrice") Double totalPrice);
    /**
     * Finds orders with the specified status.
     *
     * @param status status value to match
     * @return orders that have the given status
     */
    @Query("SELECT o FROM Order o WHERE o.status = :status")
    List<Order> findByStatus(@Param("status") Status status);
    /**
     * Finds orders placed by a specific user id.
     *
     * @param user user identifier to match
     * @return orders that belong to the given user id
     */
    @Query("SELECT o FROM Order o WHERE o.user.id = :user")
    List<Order> findByUserId(@Param("user") Long user);
}
