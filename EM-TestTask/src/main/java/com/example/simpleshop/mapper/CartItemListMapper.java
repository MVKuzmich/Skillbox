package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.CartItemReadDto;
import com.example.simpleshop.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CartItemReadMapper.class, ProductMinInfoMapper.class})
public interface CartItemListMapper {
    List<CartItemReadDto> toDtoList(List<CartItem> productList);
}
