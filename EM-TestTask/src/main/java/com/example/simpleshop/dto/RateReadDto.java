package com.example.simpleshop.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class RateReadDto {

    Long id;
    UserMinInfoDto userReadDto;
    ProductMinInfoReadDto productReadDto;
    Integer rate;
    LocalDateTime createDate;
}
