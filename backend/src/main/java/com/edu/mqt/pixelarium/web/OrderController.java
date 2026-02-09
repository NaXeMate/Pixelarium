package com.edu.mqt.pixelarium.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.mqt.pixelarium.model.Order;
import com.edu.mqt.pixelarium.model.dto.request.CreateOrderDTORequest;
import com.edu.mqt.pixelarium.model.vo.Status;
import com.edu.mqt.pixelarium.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Exposes order-related endpoints under {@code /api/orders}.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates a controller backed by the given order service.
     *
     * @param orderService service used to handle order operations
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Returns all orders.
     *
     * @return the list of orders
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Returns a single order by its identifier.
     *
     * @param id order identifier
     * @return the order with the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    
    /**
     * Creates a new order from the provided request payload.
     *
     * @param orderDTO draft order data and line items
     * @return the created order
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTORequest orderDTO) {
        Order createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    
    /**
     * Updates an existing order using the provided details.
     *
     * @param id order identifier to update
     * @param orderDetails updated order data
     * @return the updated order
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @RequestBody Order orderDetails) {
        orderDetails.setId(id);
        Order updatedOrder = orderService.updateOrder(orderDetails);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Deletes an order by its identifier.
     *
     * @param id order identifier
     * @return an empty response with {@code 204 No Content}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Changes the status of an order to the requested value.
     *
     * @param id order identifier
     * @param statusType new status type to apply
     * @return the updated order with the new status
     */
    @PutMapping("/{id}/status/{statusType}")
    public ResponseEntity<Order> changeOrderStatus(
            @PathVariable Long id,
            @PathVariable Status.StatusType statusType) {
        Status newStatus = new Status(statusType);
        Order changedStatusOrder = orderService.changeOrderStatus(id, newStatus);
        return ResponseEntity.ok(changedStatusOrder);
    }

    /**
     * Cancels an order when allowed by business rules.
     *
     * @param id order identifier
     * @return an empty response with {@code 204 No Content}
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns all orders placed by a given user.
     *
     * @param id user identifier
     * @return the list of orders created by the user
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long id) {
        List<Order> userOrders = orderService.findByUserId(id);
        return ResponseEntity.ok(userOrders);
    }
    
    /**
     * Returns orders that match the provided status.
     *
     * @param statusType status to filter by
     * @return the list of orders with the requested status
     */
    @GetMapping("/status/{statusType}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable Status.StatusType statusType) {
        Status status = new Status(statusType);
        List<Order> statusOrders = orderService.findByStatus(status);
        return ResponseEntity.ok(statusOrders);
    }


    /**
     * Returns orders with a specific order date.
     *
     * @param date order date-time in ISO-8601 format
     * @return the list of orders matching the given date-time
     * @throws java.time.format.DateTimeParseException if {@code date} is not a valid date-time
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Order>> getOrdersByDate(@PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        List<Order> orders = orderService.findByOrderDate(dateTime);
        return ResponseEntity.ok(orders);
    }

    
    /**
     * Returns orders with the specified total price.
     *
     * @param price total price to match
     * @return the list of orders with the requested total price
     */
    @GetMapping("/price/{price}")
    public ResponseEntity<List<Order>> getOrdersByPrice(@PathVariable Double price) {
        List<Order> priceOrders = orderService.findByTotalPrice(price);
        return ResponseEntity.ok(priceOrders);
    }
}
