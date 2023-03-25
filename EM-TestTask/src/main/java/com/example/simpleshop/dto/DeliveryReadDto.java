package com.example.simpleshop.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class DeliveryReadDto {

    Long id;
    LocalDateTime createDate;
    List<PurchaseReadDto> purchaseList;
    UserMinInfoDto user;
}
