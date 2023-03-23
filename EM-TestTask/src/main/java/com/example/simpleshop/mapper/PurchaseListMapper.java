package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.PurchaseReadDto;
import com.example.simpleshop.entity.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = PurchaseReadMapper.class)
public interface PurchaseListMapper {

    List<PurchaseReadDto> toDtoList(List<Purchase> purchaseList);
}
