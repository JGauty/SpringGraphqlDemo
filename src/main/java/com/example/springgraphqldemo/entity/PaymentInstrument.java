package com.example.springgraphqldemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAYMENT_INSTRUMENT")
public class PaymentInstrument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INSTRUMENT_ID")
    private Long id;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "UPPER_LIMIT")
    private BigDecimal upperLimit;

    @Column(name = "LOWER_LIMIT")
    private BigDecimal lowerLimit;

    @Column(name = "POINTS_MULTIPLIER")
    private BigDecimal pointsMultiplier;

    @OneToMany(mappedBy = "paymentInstrument")
    private List<PaymentTransaction> paymentTransaction;

}
