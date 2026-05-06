package com.product.prod.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean processPayment() {
        return new Random().nextBoolean(); // simulate success/failure
    }
}