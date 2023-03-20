package com.example.simpleshop.dto;

import jakarta.persistence.Column;
import lombok.Value;

@Value
public class DiscountReadDto {

    Long id;
    Integer discountValue;
    Integer discountDuration;
}
