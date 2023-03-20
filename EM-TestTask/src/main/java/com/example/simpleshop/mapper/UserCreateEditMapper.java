package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.UserCreateEditDto;
import com.example.simpleshop.entity.User;
import com.example.simpleshop.enums.Role;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {


    @Override
    public User map(UserCreateEditDto fromObject) {
        User user = new User();
        user.setUsername(fromObject.getUsername());
        user.setEmail(fromObject.getEmail());
        user.setPassword(fromObject.getPassword());
        user.setBalance(BigDecimal.ZERO);
        user.setRole(Role.USER);

        return user;
    }
}
