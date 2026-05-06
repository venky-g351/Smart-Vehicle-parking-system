package com.product.prod.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.prod.entity.CartItem;
import com.product.prod.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @PostMapping("/add")
    public String add(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int qty) {
        service.add(userId, productId, qty);
        return "Added to cart";
    }
   
    @GetMapping
    public List<CartItem> getCart(@RequestParam Long userId) {
        return service.getUserCart(userId);
    }

    @DeleteMapping("/clear")
    public String clear(@RequestParam Long userId) {
        service.clearCart(userId);
        return "Cart cleared";
    }
}