package com.example.simpleshop.dto;

import com.example.simpleshop.enums.Role;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class UserReadDto {
    Long id;
    String username;
    String email;
    BigDecimal balance;
    String role;


}
