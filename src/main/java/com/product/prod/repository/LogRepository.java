package com.product.prod.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.prod.entity.AuditLog;

@Repository
public interface LogRepository extends JpaRepository<AuditLog, Long> {

}
