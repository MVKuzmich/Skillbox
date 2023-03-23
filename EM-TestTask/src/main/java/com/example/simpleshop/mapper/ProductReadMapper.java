package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.ProductReadDto;
import com.example.simpleshop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring", uses = {DiscountMapper.class, CompanyMapper.class})
public interface ProductReadMapper {

    @Mapping(target = "discountReadDto", source = "discount")
    @Mapping(target = "companyReadDto", source = "company")
    ProductReadDto toProductReadDto(Product product);

}
