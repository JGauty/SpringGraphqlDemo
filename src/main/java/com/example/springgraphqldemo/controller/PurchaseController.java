package com.example.springgraphqldemo.controller;

import com.example.springgraphqldemo.model.DateRangeRequest;
import com.example.springgraphqldemo.model.PurchaseRequest;
import com.example.springgraphqldemo.model.PurchaseResponse;
import com.example.springgraphqldemo.model.TotalSalesResponse;
import com.example.springgraphqldemo.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @MutationMapping
    public PurchaseResponse purchase(@Argument @Valid PurchaseRequest purchaseRequest){
        return purchaseService.purchase(purchaseRequest);
    }

//    @MutationMapping
//    public PurchaseResponse purchase(@Argument @Valid PurchaseRequest purchaseRequest){
//        return purchaseService.purchase(purchaseRequest);
//    }

    @QueryMapping
    public List<TotalSalesResponse> sales (@Argument @Valid DateRangeRequest dateRangeRequest) {
        return purchaseService.getSales(dateRangeRequest);
    }
}
