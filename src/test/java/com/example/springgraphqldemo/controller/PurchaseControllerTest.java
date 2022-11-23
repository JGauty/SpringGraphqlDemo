package com.example.springgraphqldemo.controller;

import com.example.springgraphqldemo.config.GraphQlConfig;
import com.example.springgraphqldemo.model.DateRangeRequest;
import com.example.springgraphqldemo.model.PurchaseRequest;
import com.example.springgraphqldemo.model.PurchaseResponse;
import com.example.springgraphqldemo.model.TotalSalesResponse;
import com.example.springgraphqldemo.service.PurchaseService;
import com.example.springgraphqldemo.util.PaymentMethod;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@GraphQlTest(PurchaseController.class)
@Import(GraphQlConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PurchaseControllerTest {
    @Autowired
    GraphQlTester graphQlTester;

    @MockBean
    private PurchaseService service;

    @Test
    @Order(1)
    void testPurchaseSuccess() {

        Mockito.when(service.purchase(Mockito.any())).thenReturn(new PurchaseResponse("2400", 24));
        String str = "2022-11-18T16:25:00Z";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        PurchaseRequest purchaseRequest = new PurchaseRequest("2400", new BigDecimal(1), PaymentMethod.CASH, dateTime);
        // language=GraphQL
        graphQlTester.documentName("testpurchase")
                .variable("$purchaseRequest", purchaseRequest)
                .execute()
                .path("purchase")
                .entity(PurchaseResponse.class)
                .satisfies(response -> {
                    assertNotNull(response.getFinal_price());
                    assertNotNull(response.getPoints());
                    assertEquals(24, response.getPoints());
                    assertEquals("2400", response.getFinal_price());
                });
    }

    @Test
    public void testSalesQuerySuccess() {
        String str = "2022-11-18T16:25:00Z";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        LocalDateTime endTime = LocalDateTime.parse("2022-11-23T16:25:00Z", formatter);
        Mockito.when(service.getSales(Mockito.any())).thenReturn(
                Arrays.asList(new TotalSalesResponse(dateTime.atOffset(ZoneOffset.UTC), "10000", 100)));


        DateRangeRequest dateRangeRequest = new DateRangeRequest(dateTime, endTime);
        // language=GraphQL
        graphQlTester.documentName("testsales")
                .variable("$dateRangeRequest", dateRangeRequest)
                .execute()
                .path("sales")
                .entityList(TotalSalesResponse.class)
                .hasSize(1);
    }
}