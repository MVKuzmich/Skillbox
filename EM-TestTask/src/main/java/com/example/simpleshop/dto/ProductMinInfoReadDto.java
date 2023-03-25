package com.example.simpleshop.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductMinInfoReadDto {

    Long id;
    String name;
    CompanyReadDto companyReadDto;
    DiscountReadDto discountReadDto;

}
