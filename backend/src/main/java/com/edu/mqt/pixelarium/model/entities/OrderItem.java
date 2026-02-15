package com.edu.mqt.pixelarium.model.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;

/**
 * Represents a line item within an order.
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product productId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, name = "unit_price")
    private BigDecimal unitPrice;

    /**
     * Creates an empty order item for JPA.
     */
    public OrderItem() {}

    /**
     * Creates an order item with the provided details.
     *
     * @param orderId parent order
     * @param productId ordered product
     * @param quantity quantity ordered
     * @param unitPrice unit price applied to the order line
     */
    public OrderItem(Order orderId, Product productId, int quantity, BigDecimal unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    /**
     * Creates an order item with the provided id and details.
     *
     * @param id order item identifier
     * @param orderId parent order
     * @param productId ordered product
     * @param quantity quantity ordered
     * @param unitPrice unit price applied to the order line
     */
    public OrderItem(Long id, Order orderId, Product productId, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
