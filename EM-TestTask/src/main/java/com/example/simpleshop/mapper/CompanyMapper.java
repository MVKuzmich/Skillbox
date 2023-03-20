package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.CompanyReadDto;
import com.example.simpleshop.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper implements Mapper<Company, CompanyReadDto> {
    @Override
    public CompanyReadDto map(Company fromObject) {
        return new CompanyReadDto(
                fromObject.getId(),
                fromObject.getName()
        );
    }
}
