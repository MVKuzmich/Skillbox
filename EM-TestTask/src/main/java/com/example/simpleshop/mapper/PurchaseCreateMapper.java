package com.example.simpleshop.mapper;

import com.example.simpleshop.entity.CartItem;
import com.example.simpleshop.entity.Product;
import com.example.simpleshop.entity.Purchase;
import com.example.simpleshop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface PurchaseCreateMapper {

    @Mapping(target = "delivery", ignore = true)
    @Mapping(target = "id", ignore = true)
    Purchase toPurchase(CartItem cartItem, User user);

}
