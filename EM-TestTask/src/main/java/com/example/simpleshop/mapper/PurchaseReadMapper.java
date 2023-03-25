package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.PurchaseReadDto;
import com.example.simpleshop.entity.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMinInfoMapper.class)
public interface PurchaseReadMapper {

    @Mapping(target = "productName", source = "product.name")
    PurchaseReadDto toPurchaseReadDto(Purchase purchase);
}
