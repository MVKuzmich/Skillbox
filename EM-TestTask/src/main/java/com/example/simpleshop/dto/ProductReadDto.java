package com.example.simpleshop.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductReadDto {

    Long id;
    String name;
    String description;
    String organizationName;
    BigDecimal price;
    Long quantityInStore;
    Integer discountValue;

}
