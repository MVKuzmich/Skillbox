package com.example.simpleshop.dto;

import lombok.Value;

@Value
public class ReviewReadDto {

    Long id;
    UserMinInfoDto userReadDto;
    ProductMinInfoReadDto productReadDto;
    String description;
}
