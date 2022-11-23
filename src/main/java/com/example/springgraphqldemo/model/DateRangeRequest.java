package com.example.springgraphqldemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateRangeRequest {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
}
