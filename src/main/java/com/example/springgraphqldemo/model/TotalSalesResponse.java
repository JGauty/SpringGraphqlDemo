package com.example.springgraphqldemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalSalesResponse {
    private OffsetDateTime dateTime;
    private String sales;
    private Integer points;
}
