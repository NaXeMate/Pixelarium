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

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTORequest orderDTO) {
        Order createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @RequestBody Order orderDetails) {
        orderDetails.setId(id);
        Order updatedOrder = orderService.updateOrder(orderDetails);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status/{statusType}")
    public ResponseEntity<Order> changeOrderStatus(
            @PathVariable Long id,
            @PathVariable Status.StatusType statusType) {
        Status newStatus = new Status(statusType);
        Order changedStatusOrder = orderService.changeOrderStatus(id, newStatus);
        return ResponseEntity.ok(changedStatusOrder);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long id) {
        List<Order> userOrders = orderService.findByUserId(id);
        return ResponseEntity.ok(userOrders);
    }
    
    @GetMapping("/status/{statusType}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable Status.StatusType statusType) {
        Status status = new Status(statusType);
        List<Order> statusOrders = orderService.findByStatus(status);
        return ResponseEntity.ok(statusOrders);
    }


    @GetMapping("/date/{date}")
    public ResponseEntity<List<Order>> getOrdersByDate(@PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        List<Order> orders = orderService.findByOrderDate(dateTime);
        return ResponseEntity.ok(orders);
    }

    
    @GetMapping("/price/{price}")
    public ResponseEntity<List<Order>> getOrdersByPrice(@PathVariable Double price) {
        List<Order> priceOrders = orderService.findByTotalPrice(price);
        return ResponseEntity.ok(priceOrders);
    }
}