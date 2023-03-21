package com.example.simpleshop.dto;

import lombok.Value;

import java.util.List;

@Value
public class DeliveryReadDto {

    Long id;
    List<PurchaseReadDto> purchaseList;
    UserDeliveryDto user;
}
