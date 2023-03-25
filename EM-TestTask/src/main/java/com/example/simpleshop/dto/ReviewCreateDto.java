package com.example.simpleshop.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ReviewCreateDto {

    Long productId;
    String description;
}
