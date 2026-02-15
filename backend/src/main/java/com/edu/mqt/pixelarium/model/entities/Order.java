package com.edu.mqt.pixelarium.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.edu.mqt.pixelarium.model.vo.Status;

import jakarta.persistence.*;

/**
 * Represents a persisted order and its line items.
 */
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, name = "order_date")
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Embedded
    @AttributeOverride(name = "status", column = @Column(name = "status", nullable = false))
    private Status status;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * Creates an order with the current timestamp as the order date.
     */
    public Order() {
        this.orderDate = LocalDateTime.now();
    }

    /**
     * Creates an order with the provided data and sets the order date to now.
     *
     * @param user owning user
     * @param orderDate ignored input date; the order date is set to now
     * @param totalPrice total price for the order
     * @param status status to assign
     * @param orderItems line items to attach
     */
    public Order(User user, LocalDateTime orderDate, BigDecimal totalPrice, Status status,
            List<OrderItem> orderItems) {
        this.user = user;
        this.orderDate = LocalDateTime.now();
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderItems = orderItems;
    }

    /**
     * Creates an order with the provided id and sets the order date to now.
     *
     * @param id order identifier
     * @param user owning user
     * @param orderDate ignored input date; the order date is set to now
     * @param totalPrice total price for the order
     * @param status status to assign
     * @param orderItems line items to attach
     */
    public Order(Long id, User user, LocalDateTime orderDate, BigDecimal totalPrice, Status status,
            List<OrderItem> orderItems) {
        this.id = id;
        this.user = user;
        this.orderDate = LocalDateTime.now();
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
