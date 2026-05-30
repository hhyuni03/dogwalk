package com.example.dogwalk.point.repository;

import com.example.dogwalk.point.domain.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
}
