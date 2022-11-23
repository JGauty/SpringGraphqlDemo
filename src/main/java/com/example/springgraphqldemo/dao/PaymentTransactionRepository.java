package com.example.springgraphqldemo.dao;

import com.example.springgraphqldemo.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    public List<PaymentTransaction> findAllBySaleTimeStampBetween
            (LocalDateTime endDate, LocalDateTime startDate);
}
