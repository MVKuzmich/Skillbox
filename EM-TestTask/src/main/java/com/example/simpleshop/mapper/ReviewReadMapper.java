package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.ReviewReadDto;
import com.example.simpleshop.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMinInfoMapper.class, ProductMinInfoMapper.class})
public interface ReviewReadMapper {

    @Mapping(target = "productReadDto", source = "product")
    @Mapping(target = "userReadDto", source = "user")
    ReviewReadDto toReviewReadDto(Review review);
}
