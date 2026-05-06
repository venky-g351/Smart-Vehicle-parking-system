package com.product.prod.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.product.prod.entity.Order;
import com.product.prod.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    // PLACE ORDER
    @PostMapping("/place")
    public String place(@RequestParam Long userId) {
        service.placeOrder(userId);
        return "Order placed successfully";
    }

    // VIEW ORDERS
    @GetMapping("/view")
    public List<Order> viewOrders(@RequestParam Long userId) {
        return service.getOrdersByUser(userId);
    }

    // CANCEL SINGLE ORDER
    @DeleteMapping("/cancel/order")
    public String cancelOrder(@RequestParam Long orderId) {
        service.cancelOrder(orderId);
        return "Order cancelled successfully";
    }

    // CANCEL ALL ORDERS BY USER
    @DeleteMapping("/cancel/user")
    public String cancelOrderByUser(@RequestParam Long userId) {
        service.cancelOrderByUser(userId);
        return "All orders cancelled for user " + userId;
    }
}