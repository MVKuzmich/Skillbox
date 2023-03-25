package com.example.simpleshop.dto;

import com.example.simpleshop.entity.Purchase;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class DeliveryReadDto {

    Long id;
    LocalDateTime createDate;
    List<PurchaseReadDto> purchaseList;
    UserDeliveryDto user;
}
