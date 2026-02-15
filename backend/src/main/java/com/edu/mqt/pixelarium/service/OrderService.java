package com.edu.mqt.pixelarium.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.mqt.pixelarium.model.dto.request.CreateOrderDTORequest;
import com.edu.mqt.pixelarium.model.dto.request.OrderItemDTORequest;
import com.edu.mqt.pixelarium.model.entities.Order;
import com.edu.mqt.pixelarium.model.entities.OrderItem;
import com.edu.mqt.pixelarium.model.entities.Product;
import com.edu.mqt.pixelarium.model.vo.Status;
import com.edu.mqt.pixelarium.model.vo.Status.StatusType;
import com.edu.mqt.pixelarium.repositories.OrderRepository;

/**
 * Provides order-related business operations.
 */
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserService userService;
    private final ProductService productService;

    /**
     * Creates a service backed by the given dependencies.
     *
     * @param orderRepo repository used to persist orders
     * @param userService service used to resolve users
     * @param productService service used to resolve products
     */
    public OrderService(OrderRepository orderRepo, UserService userService, ProductService productService) {
        this.orderRepo = orderRepo;
        this.userService = userService;
        this.productService = productService;
    }

    // ========= CRRUD =========
    
    /**
     * Returns all orders.
     *
     * @return the list of orders
     */
    @Transactional(readOnly = true)
    public List<Order> getOrders() {
        return orderRepo.findAll();
    }

    /**
     * Returns an order by id, or {@code null} if it does not exist.
     *
     * @param id order identifier
     * @return the order if found, otherwise {@code null}
     */
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        Optional<Order> op = orderRepo.findById(id);

        if (op.isPresent()) {
            return op.get();
        }

        return null;
    }

    /**
     * Updates an existing order if it already exists.
     *
     * @param order order data to persist
     * @return the saved order, or {@code null} if the order does not exist
     */
    public Order updateOrder(Order order) {
        if (orderRepo.existsById(order.getId())) {
            System.out.println("Order updated in the database.");
            return orderRepo.save(order);
        } else {
            System.out.println("An error has occurred and the database has not been updated.");
            return null;
        }
    }

    /**
     * Deletes an order by id.
     *
     * @param id order identifier
     * @throws java.util.NoSuchElementException if the order does not exist
     */
    public void deleteOrder(Long id) {
        orderRepo.findById(id).orElseThrow();
        orderRepo.deleteById(id);
        System.out.println("Order successfully deleted from the database.");
    }

    // ========= CUSTOM METHODS =========

    /**
     * Creates a new order in {@link StatusType#DRAFT} from the draft payload.
     *
     * @param draftOrder request payload with user and item data
     * @return the created order
     */
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

    /**
     * Builds an order item using the product's sale price when available.
     *
     * @param order parent order
     * @param itemDTO item request data
     * @param product resolved product
     * @return the built order item
     */
    private OrderItem buildOrderItem(Order order, OrderItemDTORequest itemDTO, Product product) {
        if (product.getSalePrice() == null) {
            OrderItem orderItem = new OrderItem(order, product, itemDTO.quantity(), product.getPrice());
            return orderItem;
        }
        
        OrderItem orderItem = new OrderItem(order, product, itemDTO.quantity(), product.getSalePrice());
        return orderItem;
    }

    /**
     * Changes an order status if the transition is allowed.
     *
     * @param orderId order identifier
     * @param newStatus new status to set
     * @return the updated order, or {@code null} if the transition is not allowed
     */
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

    /**
     * Cancels an order when it is still pending.
     *
     * @param orderId order identifier
     */
    public void cancelOrder(Long orderId) {
        Order canceledOrder = getOrderById(orderId);

        if (canceledOrder.getStatus().getType() == Status.StatusType.PENDING) {
            deleteOrder(orderId);
        } else {
            System.out.println("This order can't be cancelled");
        }
    }

    /**
     * Returns orders with the exact order date-time.
     *
     * @param orderDate order date-time to match
     * @return matching orders
     */
    @Transactional(readOnly = true)
    public List<Order> findByOrderDate(LocalDateTime orderDate) {
        return orderRepo.findByOrderDate(orderDate);
    }

    /**
     * Returns orders with the exact total price.
     *
     * @param totalPrice total price to match
     * @return matching orders
     */
    @Transactional(readOnly = true)
    public List<Order> findByTotalPrice(Double totalPrice) {
        return orderRepo.findByTotalPrice(totalPrice);
    }

    /**
     * Returns orders with the given status.
     *
     * @param status status to match
     * @return matching orders
     */
    @Transactional(readOnly = true)
    public List<Order> findByStatus(Status status) {
        return orderRepo.findByStatus(status);
    }

    /**
     * Returns orders placed by the given user id.
     *
     * @param userId user identifier
     * @return matching orders
     */
    @Transactional(readOnly = true)
    public List<Order> findByUserId(Long userId) {
        return orderRepo.findByUserId(userId);
    }
}
