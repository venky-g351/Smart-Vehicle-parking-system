package com.product.prod.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.prod.entity.Product;
import com.product.prod.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/test")
    public String test() {
        return "Working Fine";
    }

    @PostMapping
    public Product add(@RequestBody Product p) {
        return service.add(p);
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }
}