package com.example.simpleshop.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ProductMinInfoReadDto {

    Long id;
    String name;
    CompanyReadDto companyReadDto;
    DiscountReadDto discountReadDto;

}
