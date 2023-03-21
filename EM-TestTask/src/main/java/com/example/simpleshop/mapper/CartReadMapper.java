package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.CartReadDto;
import com.example.simpleshop.entity.PurchaseCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartReadMapper implements Mapper<PurchaseCart, CartReadDto> {

    private final PurchaseReadMapper purchaseReadMapper;
    private final UserReadMapper userReadMapper;

    @Override
    public CartReadDto map(PurchaseCart fromObject) {
        return new CartReadDto(
                fromObject.getId(),
                fromObject.getCreateDate(),
                fromObject.getPurchaseList().stream()
                        .map(purchaseReadMapper::map)
                        .collect(Collectors.toList()),
                userReadMapper.map(fromObject.getUser()));
    }
}
