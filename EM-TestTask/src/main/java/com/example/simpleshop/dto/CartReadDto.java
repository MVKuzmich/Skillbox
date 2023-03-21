package com.example.simpleshop.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class CartReadDto {

    Long id;
    LocalDateTime createDate;
    List<PurchaseReadDto> purchaseList;
    UserReadDto userReadDto;
}
