package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.UserDeliveryDto;
import com.example.simpleshop.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserDeliveryMapper {

    UserDeliveryDto toUserDeliveryDto(User user);
}
