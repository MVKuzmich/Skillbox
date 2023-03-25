package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.CartItemReadDto;
import com.example.simpleshop.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMinInfoMapper.class)
public interface CartItemReadMapper {

    @Mapping(target = "productDescription", source = "product")
    CartItemReadDto toCartItemReadDto(CartItem cartItem);
}
