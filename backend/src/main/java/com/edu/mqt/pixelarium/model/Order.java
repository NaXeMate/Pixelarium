package com.edu.mqt.pixelarium.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.edu.mqt.pixelarium.model.vo.Status;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(nullable = false, name = "order_date")
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private Double totalPrice;

    @Embedded
    @AttributeOverride(name = "status", column = @Column(name = "status", nullable = false))
    private Status status;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL,
               fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {}

    public Order(Long id, User userId, LocalDateTime orderDate, Double totalPrice, Status status,
            List<OrderItem> orderItems) {
        this.id = id;
        this.userId = userId;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
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
