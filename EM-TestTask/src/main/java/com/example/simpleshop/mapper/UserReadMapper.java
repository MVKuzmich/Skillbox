package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.UserReadDto;
import com.example.simpleshop.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserReadMapper {

    UserReadDto toUserReadDto(User user);
}

