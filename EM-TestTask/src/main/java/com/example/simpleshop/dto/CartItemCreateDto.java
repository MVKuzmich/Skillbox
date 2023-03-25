package com.example.simpleshop.dto;

import lombok.Value;

@Value
public class CartItemCreateDto {
    String productName;
    Integer productAmount;
}
