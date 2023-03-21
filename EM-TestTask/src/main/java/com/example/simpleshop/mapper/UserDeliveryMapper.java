package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.UserDeliveryDto;
import com.example.simpleshop.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserDeliveryMapper implements Mapper<User, UserDeliveryDto> {
    @Override
    public UserDeliveryDto map(User fromObject) {
        return new UserDeliveryDto(
                fromObject.getId(),
                fromObject.getUsername(),
                fromObject.getEmail()
        );
    }
}
