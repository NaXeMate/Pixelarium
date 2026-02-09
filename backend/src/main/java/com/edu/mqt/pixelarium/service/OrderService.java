package com.edu.mqt.pixelarium.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.mqt.pixelarium.model.Order;
import com.edu.mqt.pixelarium.model.OrderItem;
import com.edu.mqt.pixelarium.model.Product;
import com.edu.mqt.pixelarium.model.dto.request.CreateOrderDTORequest;
import com.edu.mqt.pixelarium.model.dto.request.OrderItemDTORequest;
import com.edu.mqt.pixelarium.model.vo.Status;
import com.edu.mqt.pixelarium.model.vo.Status.StatusType;
import com.edu.mqt.pixelarium.repositories.OrderRepository;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserService userService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepo, UserService userService, ProductService productService) {
        this.orderRepo = orderRepo;
        this.userService = userService;
        this.productService = productService;
    }

    // ========= CRRUD =========
    
    @Transactional(readOnly = true)
    public List<Order> getOrders() {
        return orderRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        Optional<Order> op = orderRepo.findById(id);

        if (op.isPresent()) {
            return op.get();
        }

        return null;
    }

    public Order updateOrder(Order order) {
        if (orderRepo.existsById(order.getId())) {
            System.out.println("Order updated in the database.");
            return orderRepo.save(order);
        } else {
            System.out.println("An error has occurred and the database has not been updated.");
            return null;
        }
    }

    public void deleteOrder(Long id) {
        orderRepo.findById(id).orElseThrow();
        orderRepo.deleteById(id);
        System.out.println("Order successfully deleted from the database.");
    }

    // ========= CUSTOM METHODS =========

    public Order createOrder (CreateOrderDTORequest draftOrder) {
        Long orderUserId = draftOrder.userId();
        List<OrderItem> items = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        Order newOrder = new Order();
        
        newOrder.setUser(userService.getUserById(orderUserId));
        newOrder.setStatus(new Status(StatusType.DRAFT));
        
        for (OrderItemDTORequest itemDTO : draftOrder.items()) {
            
            Product product = productService.getProductById(itemDTO.productId());

            OrderItem orderItem = buildOrderItem(newOrder, itemDTO, product);

            items.add(orderItem);
        }
        
        newOrder.setOrderItems(items);

        for (OrderItem item : items) {
            BigDecimal unitPrice = item.getUnitPrice();
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            BigDecimal lineTotal = unitPrice.multiply(quantity);

            totalPrice = totalPrice.add(lineTotal);
        }

        newOrder.setTotalPrice(totalPrice);

        return orderRepo.save(newOrder);
    }

    private OrderItem buildOrderItem(Order order, OrderItemDTORequest itemDTO, Product product) {
        if (product.getSalePrice() == null) {
            OrderItem orderItem = new OrderItem(order, product, itemDTO.quantity(), product.getPrice());
            return orderItem;
        }
        
        OrderItem orderItem = new OrderItem(order, product, itemDTO.quantity(), product.getSalePrice());
        return orderItem;
    }

    public Order changeOrderStatus(Long orderId, Status newStatus) {
        Order changingOrder = getOrderById(orderId);

        if (changingOrder.getStatus().getType() == StatusType.PENDING && (newStatus.getType() == StatusType.DRAFT 
        || newStatus.getType() == StatusType.DELIVERED)) {
            System.out.println("The new order status is not compatible with the current one. No changes have been made.");
            return null;
        }
        
        if (changingOrder.getStatus().getType() == StatusType.SENT && (newStatus.getType() == StatusType.PENDING
         || newStatus.getType() == StatusType.DRAFT)) {
            System.out.println("The new order status is not compatible with the current one. No changes have been made.");
            return null;
        }

        if (changingOrder.getStatus().getType() == StatusType.DELIVERED && (newStatus.getType() == StatusType.SENT 
        || newStatus.getType() == StatusType.DRAFT)) {
            System.out.println("The new order status is not compatible with the current one. No changes have been made.");
            return null;
        }

        changingOrder.setStatus(newStatus);
        return orderRepo.save(changingOrder);
    }

    public void cancelOrder(Long orderId) {
        Order canceledOrder = getOrderById(orderId);

        if (canceledOrder.getStatus().getType() == Status.StatusType.PENDING) {
            deleteOrder(orderId);
        } else {
            System.out.println("This order can't be cancelled");
        }
    }

    @Transactional(readOnly = true)
    public List<Order> findByOrderDate(LocalDateTime orderDate) {
        return orderRepo.findByOrderDate(orderDate);
    }

    @Transactional(readOnly = true)
    public List<Order> findByTotalPrice(Double totalPrice) {
        return orderRepo.findByTotalPrice(totalPrice);
    }

    @Transactional(readOnly = true)
    public List<Order> findByStatus(Status status) {
        return orderRepo.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Order> findByUserId(Long userId) {
        return orderRepo.findByUserId(userId);
    }
}