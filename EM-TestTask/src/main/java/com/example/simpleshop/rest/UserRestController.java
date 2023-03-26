package com.example.simpleshop.rest;

import com.example.simpleshop.dto.*;
import com.example.simpleshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

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

    @PostMapping("/replenish-balance")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserReadDto replenishBalance(@RequestBody ReplenishBalanceDto replenishBalanceDto) {
        return userService.replenishBalance(replenishBalanceDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<UserReadDto> findAll(Pageable pageable) {
        return PageResponse.of(userService.findAll(pageable));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserReadDto findById(@PathVariable("userId") Long userId) {
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("userId") Long userId) {
        if(!userService.deleteUser(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/messages")
    public MessageSendReport sendMessageAllUsers(@RequestBody MessageDto messageDto) {
        return userService.sendMessageAllUsers(messageDto);
    }



}
