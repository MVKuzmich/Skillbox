package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.PurchaseReadDto;
import com.example.simpleshop.entity.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductReadMapper.class})
public interface PurchaseReadMapper {

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "id", source = "id")
    PurchaseReadDto toPurchaseReadDto(Purchase purchase);
}
