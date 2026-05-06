package com.product.prod.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.product.prod.entity.CartItem;
import com.product.prod.repository.CartRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository repo;
    private final InventoryService inventory;

    // ADD ITEM TO CART
    public void add(Long userId, Long productId, int qty) {

        // reserve stock correctly
        inventory.reserveStock(productId, qty);

        repo.save(new CartItem(null, userId, productId, qty));
    }

    // GET CART ITEMS
    public List<CartItem> getCart(Long userId) {
        return repo.findAll().stream()
                .filter(c -> c.getUserId().equals(userId))
                .toList();
    }

    public List<CartItem> getUserCart(Long userId) {
        return getCart(userId);
    }

    // CLEAR CART
    public void clearCart(Long userId) {
        repo.deleteAll(getCart(userId));
    }
}