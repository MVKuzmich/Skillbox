package com.example.simpleshop.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CartItemReadDto {

    Long id;
    ProductMinInfoReadDto productDescription;
    Integer productAmount;
    BigDecimal totalPrice;
}
