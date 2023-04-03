package com.example.simpleshop.rest;

import com.example.simpleshop.dto.RateCreateDto;
import com.example.simpleshop.dto.RateReadDto;
import com.example.simpleshop.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rates")
public class RateRestController {

    private final RateService rateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public final RateReadDto addRate(@RequestBody @Validated RateCreateDto rateCreateDto, Principal principal) {
        return rateService.addRate(rateCreateDto, principal);
    }

}
