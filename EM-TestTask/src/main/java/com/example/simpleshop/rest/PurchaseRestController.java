package com.example.simpleshop.rest;

import com.example.simpleshop.dto.CartReadDto;
import com.example.simpleshop.dto.DeliveryReadDto;
import com.example.simpleshop.dto.PurchaseCreateDto;
import com.example.simpleshop.dto.PurchaseReadDto;
import com.example.simpleshop.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseRestController {

    private final PurchaseService purchaseService;

    @PostMapping("/carts/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CartReadDto createCart(Principal principal) {
        return purchaseService.createCart(principal);
    }


    @PostMapping("/postpone")
    public CartReadDto postpone(@RequestBody PurchaseCreateDto purchaseDto,
                                    Principal principal) {
        return purchaseService.postponeIntoCart(purchaseDto, principal);
    }

    @PostMapping("/pay")
    public DeliveryReadDto payCart(Principal principal) {
         return purchaseService.payAndPassToDelivery(principal);
    }




}
