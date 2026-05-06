package com.product.prod.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.product.prod.entity.Product;
import com.product.prod.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;

    public Product add(Product p) {
        return repo.save(p);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }
}