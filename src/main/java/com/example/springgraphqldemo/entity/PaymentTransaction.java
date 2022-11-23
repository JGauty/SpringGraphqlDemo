package com.example.springgraphqldemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PAYMENT_TRANSACTION")
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "SALE")
    private BigDecimal sale;

    @Column(name = "POINTS")
    private Integer points;

    @ManyToOne
    @JoinColumn(name = "INSTRUMENT_ID", nullable = false)
    private PaymentInstrument paymentInstrument;

    @Column(name = "TRANSACTION_TIMESTAMP")
    private LocalDateTime saleTimeStamp;

}
