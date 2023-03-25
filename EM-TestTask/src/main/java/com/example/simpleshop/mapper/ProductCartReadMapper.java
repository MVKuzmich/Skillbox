package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.ProductCartReadDto;
import com.example.simpleshop.entity.ProductCart;
import com.example.simpleshop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {UserReadMapper.class, CartItemReadMapper.class, CartItemListMapper.class, ProductMinInfoMapper.class})
public interface ProductCartReadMapper {

    @Mapping(target = "userReadDto", source = "user")
    @Mapping(target = "id", source = "productCart.id")
    ProductCartReadDto toCartReadDto(ProductCart productCart, User user);

}
