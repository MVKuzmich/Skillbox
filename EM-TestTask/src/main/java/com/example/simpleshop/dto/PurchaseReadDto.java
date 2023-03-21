package com.example.simpleshop.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class PurchaseReadDto {

    Long id;
    String productName;
    Integer productAmount;
    BigDecimal totalPrice;
}
