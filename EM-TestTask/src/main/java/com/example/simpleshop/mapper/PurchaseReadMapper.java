package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.PurchaseReadDto;
import com.example.simpleshop.entity.Purchase;
import org.springframework.stereotype.Component;

@Component
public class PurchaseReadMapper implements Mapper<Purchase, PurchaseReadDto> {
    @Override
    public PurchaseReadDto map(Purchase fromObject) {
        return new PurchaseReadDto(
                fromObject.getId(),
                fromObject.getProduct().getName(),
                fromObject.getProductAmount(),
                fromObject.getTotalPrice()
        );
    }
}
