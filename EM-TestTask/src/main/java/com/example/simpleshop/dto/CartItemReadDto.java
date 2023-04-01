package com.example.simpleshop.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CartItemReadDto {

    Long id;
    ProductMinInfoReadDto productDescription;
    Integer productAmount;
    BigDecimal totalPrice;
}
