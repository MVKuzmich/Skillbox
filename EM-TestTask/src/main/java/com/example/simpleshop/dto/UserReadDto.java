package com.example.simpleshop.dto;

import com.example.simpleshop.enums.Role;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class UserReadDto {
    Long id;
    String username;
    String email;
    BigDecimal balance;
    String role;


}
