package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.UserReadDto;
import com.example.simpleshop.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User fromObject) {
        return new UserReadDto(
                fromObject.getId(),
                fromObject.getUsername(),
                fromObject.getEmail(),
                fromObject.getBalance(),
                fromObject.getRole().name());
    }
}
