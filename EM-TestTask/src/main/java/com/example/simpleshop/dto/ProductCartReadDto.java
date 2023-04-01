package com.example.simpleshop.dto;

import com.example.simpleshop.entity.CartItem;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class ProductCartReadDto {

    Long id;
    LocalDateTime createDate;
    List<CartItemReadDto> productList;
    UserReadDto userReadDto;

}
