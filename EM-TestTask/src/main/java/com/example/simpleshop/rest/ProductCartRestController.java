package com.example.simpleshop.rest;

import com.example.simpleshop.dto.CartItemCreateDto;
import com.example.simpleshop.dto.DeliveryReadDto;
import com.example.simpleshop.dto.ProductCartReadDto;
import com.example.simpleshop.service.ProductCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class ProductCartRestController {

    private final ProductCartService productCartService;

    @PostMapping("/carts/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCartReadDto createCart(Principal principal) {
        return productCartService.createCart(principal);
    }


    @PostMapping("/postpone")
    public ProductCartReadDto postpone(@RequestBody CartItemCreateDto cartItemCreateDto,
                                    Principal principal) {
        return productCartService.postpone(cartItemCreateDto, principal);
    }

    @PostMapping("/pay")
    public DeliveryReadDto payCart(Principal principal) {
         return productCartService.payAndPassToDelivery(principal);
    }




}
