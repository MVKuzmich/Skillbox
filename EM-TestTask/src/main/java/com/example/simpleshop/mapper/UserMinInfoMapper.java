package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.UserMinInfoDto;
import com.example.simpleshop.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMinInfoMapper {

    UserMinInfoDto toUserMinInfoDto(User user);
}
