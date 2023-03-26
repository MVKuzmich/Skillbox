package com.example.simpleshop.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ReplenishBalanceDto {

    Long userId;
    BigDecimal replenishAmount;
}
