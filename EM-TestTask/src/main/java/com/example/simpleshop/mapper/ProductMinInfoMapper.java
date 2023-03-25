package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.ProductMinInfoReadDto;
import com.example.simpleshop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class, DiscountMapper.class})
public interface ProductMinInfoMapper {

    @Mapping(target = "discountReadDto", source = "discount")
    @Mapping(target = "companyReadDto", source = "company")
    ProductMinInfoReadDto toProductMinInfoDto(Product product);
}
