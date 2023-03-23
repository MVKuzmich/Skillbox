package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.CartReadDto;
import com.example.simpleshop.entity.PurchaseCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {UserReadMapper.class, PurchaseReadMapper.class, PurchaseListMapper.class})
public interface CartReadMapper {

    @Mapping(target = "userReadDto", source = "user")
    CartReadDto toCartReadDto(PurchaseCart purchaseCart);

}
