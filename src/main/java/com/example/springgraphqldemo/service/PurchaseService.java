package com.example.springgraphqldemo.service;

import com.example.springgraphqldemo.dao.PaymentInstrumentRepository;
import com.example.springgraphqldemo.dao.PaymentTransactionRepository;
import com.example.springgraphqldemo.entity.PaymentInstrument;
import com.example.springgraphqldemo.entity.PaymentTransaction;
import com.example.springgraphqldemo.exception.PaymentException;
import com.example.springgraphqldemo.model.DateRangeRequest;
import com.example.springgraphqldemo.model.PurchaseRequest;
import com.example.springgraphqldemo.model.PurchaseResponse;
import com.example.springgraphqldemo.model.TotalSalesResponse;
import com.example.springgraphqldemo.util.PaymentMethod;
import com.example.springgraphqldemo.util.UtilHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.EnumerationUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transaction;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseService {

    private final PaymentInstrumentRepository paymentInstrumentRepository;

    private final PaymentTransactionRepository paymentTransactionRepository;

    public PurchaseResponse purchase(PurchaseRequest request) {
        log.info("Purchase payment request {}", request.toString());
        isValidPaymentMethod(request.getPayment_method().toString());
        PaymentInstrument paymentInstrument = paymentInstrumentRepository.findByPaymentType(request
                .getPayment_method().toString());
        log.info("Found payment instrument by type: {}", paymentInstrument.getPaymentType());
        BigDecimal upperLimit = paymentInstrument.getUpperLimit();
        BigDecimal lowerLimit = paymentInstrument.getLowerLimit();
        if(StringUtils.isBlank(request.getPrice())) {
            log.error("Price may not be empty.");
            throw new PaymentException("Price may not be empty.");
        }
        if (UtilHelper.isBetween(request.getPrice_modifier(), lowerLimit, upperLimit)) {
            BigDecimal finalPrice = new BigDecimal(request.getPrice())
                    .multiply(request.getPrice_modifier())
                    .setScale(2, RoundingMode.UP);
            BigDecimal points = new BigDecimal(request.getPrice()).multiply(paymentInstrument.getPointsMultiplier());

            PaymentTransaction transaction = new PaymentTransaction();
            transaction.setPoints(points.intValue());
            transaction.setSaleTimeStamp(request.getDateTime());
            transaction.setSale(finalPrice);
            transaction.setPaymentInstrument(paymentInstrument);
            transaction = paymentTransactionRepository.save(transaction);
            log.info("Transaction saved with id {}", transaction.getId());
            PurchaseResponse purchaseResponse = new PurchaseResponse();
            purchaseResponse.setFinal_price(finalPrice.toString());
            purchaseResponse.setPoints(points.intValue());
            log.info("Payment successful: {}", purchaseResponse);
            return purchaseResponse;
        } else {
            log.error("Price modifier is not between acceptable range for given payment method.");
            throw new PaymentException("Price modifier is not between acceptable range for given payment method.");
        }
    }

    private void isValidPaymentMethod(String payment_method) {
        if (!EnumUtils.isValidEnum(PaymentMethod.class, payment_method))
            throw new PaymentException(payment_method + " is not a valid payment method");
    }

    public List<TotalSalesResponse> getSales(DateRangeRequest request) {
        log.info("request : {}", request.toString());
        List<PaymentTransaction> transactions = paymentTransactionRepository
                .findAllBySaleTimeStampBetween(
                        request.getStartDateTime(), request.getEndDateTime());
        log.info("Found {} sales records ", transactions.size());
        return transactions.stream().map(t -> new TotalSalesResponse(t.getSaleTimeStamp().atOffset(ZoneOffset.UTC),
                t.getSale().toString(), t.getPoints())).collect(Collectors.toList());
    }

}
