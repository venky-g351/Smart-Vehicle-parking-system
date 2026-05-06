package com.product.prod.controller;

 
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.product.prod.entity.Product;
import com.product.prod.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    // ADD PRODUCT
    @PostMapping("/add")
    public Product add(@RequestBody Product product) {
        return service.addProduct(product);
    }

    // GET ALL PRODUCTS
    @GetMapping("/all")
    public List<Product> getAll() {
        return service.getAllProducts();
    }

    // GET PRODUCT BY ID
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.getProduct(id);
    }

    // RESERVE STOCK
    @PostMapping("/reserve")
    public String reserve(@RequestParam Long productId, @RequestParam int qty) {
        service.reserveStock(productId, qty);
        return "Stock reserved";
    }

    // RESTORE STOCK
    @PostMapping("/restore")
    public String restore(@RequestParam Long productId, @RequestParam int qty) {
        service.restoreStock(productId, qty);
        return "Stock restored";
    }

    // LOW STOCK ALERT
    @GetMapping("/low-stock")
    public List<Product> lowStock() {
        return service.getLowStockProducts();
    }
}