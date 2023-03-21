package com.example.simpleshop.dto;

import lombok.Value;

@Value
public class PurchaseCreateDto {
    String productName;
    Integer productAmount;
}
