package com.example.simpleshop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class RateCreateDto {

    Long productId;

    @Min(value = 1, message = "Min value must be 1")
    @Max(value = 5, message = "Max value must be 5")
    Integer rate;
}
