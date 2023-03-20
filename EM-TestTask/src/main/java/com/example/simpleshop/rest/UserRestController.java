package com.example.simpleshop.rest;

import com.example.simpleshop.dto.UserCreateEditDto;
import com.example.simpleshop.dto.UserReadDto;
import com.example.simpleshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@RequestBody UserCreateEditDto user) {
        return userService.create(user);
    }
}
