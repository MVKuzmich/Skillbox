package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.CartItemReadDto;
import com.example.simpleshop.dto.PurchaseReadDto;
import com.example.simpleshop.entity.Purchase;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PurchaseReadMapper.class, ProductMinInfoMapper.class})
public interface PurchaseListMapper {

    List<PurchaseReadDto> toPurchaseReadDtoList(List<Purchase> purchaseList);
}
