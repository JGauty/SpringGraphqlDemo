package com.example.springgraphqldemo.dao;

import com.example.springgraphqldemo.entity.PaymentInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInstrumentRepository extends JpaRepository<PaymentInstrument, Long> {
    PaymentInstrument findByPaymentType(String paymentType);
}
