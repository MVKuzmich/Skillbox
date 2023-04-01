package com.example.simpleshop.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class RateReadDto {

    Long id;
    UserMinInfoDto userReadDto;
    ProductMinInfoReadDto productReadDto;
    Integer rate;
    LocalDateTime createDate;
}
