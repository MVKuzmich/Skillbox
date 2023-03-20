package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.DiscountReadDto;
import com.example.simpleshop.entity.Discount;
import org.springframework.stereotype.Component;

@Component
public class DiscountMapper implements Mapper<Discount, DiscountReadDto> {
    @Override
    public DiscountReadDto map(Discount fromObject) {
        return new DiscountReadDto(
                fromObject.getId(),
                fromObject.getDiscountValue(),
                fromObject.getDiscountDuration()
        );
    }
}
