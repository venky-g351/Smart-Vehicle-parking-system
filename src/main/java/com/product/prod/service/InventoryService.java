package com.product.prod.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.product.prod.entity.Product;
import com.product.prod.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepo;

    // ADD PRODUCT
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    // GET ALL PRODUCTS
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    // GET PRODUCT BY ID
    public Product getProduct(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // RESERVE STOCK (ORDER PLACE)
    public void reserveStock(Long productId, int qty) {

        Product product = getProduct(productId);

        if (product.getStock() < qty) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStock(product.getStock() - qty);
        productRepo.save(product);
    }

    // RESTORE STOCK (CANCEL / RETURN)
    public void restoreStock(Long productId, int qty) {

        Product product = getProduct(productId);

        product.setStock(product.getStock() + qty);
        productRepo.save(product);
    }

    // LOW STOCK ALERT
    public List<Product> getLowStockProducts() {
        return productRepo.findByStockLessThanEqual(5);
    }
}