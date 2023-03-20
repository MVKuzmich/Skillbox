package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.ProductReadDto;
import com.example.simpleshop.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {

    private final CompanyMapper companyMapper;
    private final DiscountMapper discountMapper;

    @Override
    public ProductReadDto map(Product fromObject) {
        return new ProductReadDto(
                fromObject.getId(),
                fromObject.getName(),
                fromObject.getDescription(),
                companyMapper.map(fromObject.getCompany()).getName(),
                fromObject.getPrice(),
                fromObject.getQuantityInStore(),
                discountMapper.map(fromObject.getDiscount()).getDiscountValue()
        );

    }
}
