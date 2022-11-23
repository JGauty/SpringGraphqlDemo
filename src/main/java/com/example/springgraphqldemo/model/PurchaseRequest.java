package com.example.springgraphqldemo.model;

import com.example.springgraphqldemo.util.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class PurchaseRequest {
    @NotEmpty(message = "Price may not be empty")
    private String price;
    @NotNull
    private BigDecimal price_modifier;
    @NotEmpty(message = "Payment method may not be empty")
    private PaymentMethod payment_method;
    @NotNull
    private LocalDateTime dateTime;

}
