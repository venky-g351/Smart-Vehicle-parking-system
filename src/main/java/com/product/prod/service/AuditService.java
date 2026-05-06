package com.product.prod.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.product.prod.entity.AuditLog;
import com.product.prod.repository.LogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final LogRepository repo;

    public void log(String message) {
        AuditLog log = new AuditLog(null, message, LocalDateTime.now().toString());
        repo.save(log);
    }
}
