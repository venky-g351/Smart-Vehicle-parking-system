package com.product.prod.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.product.prod.entity.CartItem;
import com.product.prod.entity.Order;
import com.product.prod.entity.OrderStatus;
import com.product.prod.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepo;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final AuditService auditService;

    // PLACE ORDER
    @Transactional
    public void placeOrder(Long userId) {

        List<CartItem> cart = cartService.getUserCart(userId);

        if (cart.isEmpty())
            throw new RuntimeException("Cart empty");

        double total = cart.stream()
                .mapToDouble(c -> c.getQuantity() * 100)
                .sum();

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.CREATED);

        order = orderRepo.save(order);

        auditService.log("Order Created: " + order.getId());

        boolean payment = paymentService.processPayment();

        if (!payment) {

            cart.forEach(c ->
                inventoryService.restoreStock(c.getProductId(), c.getQuantity())
            );

            order.setStatus(OrderStatus.FAILED);
            orderRepo.save(order);

            throw new RuntimeException("Payment Failed");
        }

        order.setStatus(OrderStatus.PAID);
        orderRepo.save(order);

        cartService.clearCart(userId);

        auditService.log("Payment Success for Order: " + order.getId());
    }

    // VIEW ORDERS BY USER
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    // CANCEL SINGLE ORDER
    public void cancelOrder(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepo.save(order);

        auditService.log("Order Cancelled: " + orderId);
    }

    // CANCEL ALL ORDERS BY USER ✅ (FIX ADDED)
    public void cancelOrderByUser(Long userId) {

        List<Order> orders = orderRepo.findByUserId(userId);

        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found for user");
        }

        for (Order order : orders) {

            if (order.getStatus() == OrderStatus.CANCELLED) {
                continue;
            }

            order.setStatus(OrderStatus.CANCELLED);
            orderRepo.save(order);

            auditService.log("Order Cancelled: " + order.getId() + " for user " + userId);
        }
    }
}