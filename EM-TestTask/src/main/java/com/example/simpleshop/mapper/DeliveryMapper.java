package com.example.simpleshop.mapper;


import com.example.simpleshop.dto.DeliveryReadDto;
import com.example.simpleshop.entity.Delivery;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DeliveryMapper implements Mapper<Delivery, DeliveryReadDto> {

    private final PurchaseReadMapper purchaseReadMapper;
    private final UserMinInfoMapper userMinInfoMapper;
    @Override
    public DeliveryReadDto map(Delivery fromObject) {
        return new DeliveryReadDto(
                fromObject.getId(),
                fromObject.getCreateDate(),
                fromObject.getPurchaseList().stream()
                        .map(purchaseReadMapper::toPurchaseReadDto).collect(Collectors.toList()),
                userMinInfoMapper.toUserMinInfoDto(fromObject.getUser())
        );
    }
}
