package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.RateReadDto;
import com.example.simpleshop.entity.Rate;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {UserMinInfoMapper.class, ProductMinInfoMapper.class})
public interface RateReadMapper {

    @Mapping(target = "rate", source = "value")
    @Mapping(target = "userReadDto", source = "user")
    @Mapping(target = "productReadDto", source = "product")
    RateReadDto toRateReadDto(Rate rate);

}