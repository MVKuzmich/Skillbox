package com.example.simpleshop.rest;

import com.example.simpleshop.dto.PageResponse;
import com.example.simpleshop.dto.PurchaseReadDto;
import com.example.simpleshop.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    public PageResponse<PurchaseReadDto> findAll(Principal principal,
                                                 Pageable pageable) {
        return PageResponse.of(purchaseService.findAll(principal, pageable));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<PurchaseReadDto> findAllByUserId(@PathVariable("userId") Long userId,
                                                 Pageable pageable) {
        return PageResponse.of(purchaseService.findAllByUserId(userId, pageable));


    }
}
