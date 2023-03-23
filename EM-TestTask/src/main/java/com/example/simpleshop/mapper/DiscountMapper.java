package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.DiscountCreateEditDto;
import com.example.simpleshop.dto.DiscountReadDto;
import com.example.simpleshop.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiscountMapper {

    DiscountReadDto toDiscountReadDto(Discount discount);

    @Mapping(target = "discountProducts", ignore = true)
    Discount toDiscount(DiscountCreateEditDto discountCreateEditDto);



}
