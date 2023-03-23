package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.CompanyReadDto;
import com.example.simpleshop.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper {

    CompanyReadDto toCompanyReadDto(Company company);
}
