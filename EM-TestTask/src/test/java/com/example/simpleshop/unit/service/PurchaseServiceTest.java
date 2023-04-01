package com.example.simpleshop.unit.service;

import com.example.simpleshop.dto.*;
import com.example.simpleshop.entity.*;
import com.example.simpleshop.mapper.PurchaseReadMapper;
import com.example.simpleshop.repository.PurchaseRepository;
import com.example.simpleshop.repository.UserRepository;
import com.example.simpleshop.service.PurchaseService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private PurchaseReadMapper purchaseReadMapper;
    @Mock
    private UserRepository userRepository;


    Product bread;
    Product milk;
    Company bakery;
    Company milkFactory;
    Purchase purchaseBread;
    Purchase purchaseMilk;
    PurchaseReadDto purchaseReadDtoBread;
    PurchaseReadDto purchaseReadDtoMilk;


    @BeforeEach
    void init() {

        bakery = Company.builder().id(1L).name("Bakery").build();
        milkFactory = Company.builder().id(2L).name("Milk Factory").build();

        bread = Product.builder()
                .name("Bread")
                .id(1L)
                .description("text")
                .price(BigDecimal.valueOf(10L))
                .company(bakery)
                .build();

        milk = Product.builder()
                .name("Milk")
                .id(2L)
                .description("text")
                .price(BigDecimal.valueOf(20L))
                .company(milkFactory)
                .build();

        purchaseBread = Purchase.builder()
                .id(1L)
                .product(bread)
                .productAmount(2)
                .totalPrice(bread.getPrice().multiply(BigDecimal.valueOf(2L)))
                .build();

        purchaseMilk = Purchase.builder()
                .id(2L)
                .product(milk)
                .productAmount(5)
                .totalPrice(milk.getPrice().multiply(BigDecimal.valueOf(5L)))
                .build();

        purchaseReadDtoBread = new PurchaseReadDto(
                purchaseBread.getId(), purchaseBread.getProduct().getName(),
                purchaseBread.getProductAmount(), purchaseBread.getTotalPrice());

        purchaseReadDtoMilk = new PurchaseReadDto(
                purchaseMilk.getId(), purchaseMilk.getProduct().getName(),
                purchaseMilk.getProductAmount(), purchaseMilk.getTotalPrice());


    }

    @ParameterizedTest
    @CsvSource("1, 0, 2")
    void checkFindAll(Long userId, Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        doReturn(new PageImpl<>(List.of(purchaseBread, purchaseMilk), pageable, 2)).when(purchaseRepository).findAllByUserId(userId, pageable);
        doReturn(purchaseReadDtoBread).when(purchaseReadMapper).toPurchaseReadDto(purchaseBread);
        doReturn(purchaseReadDtoMilk).when(purchaseReadMapper).toPurchaseReadDto(purchaseMilk);

        Assertions.assertThat(purchaseService.findAllByUserId(userId, pageable))
                .isEqualTo(new PageImpl<>(List.of(purchaseReadDtoBread, purchaseReadDtoMilk), pageable, 2));

        verify(purchaseRepository, times(1)).findAllByUserId(userId, pageable);
        verify(purchaseReadMapper, times(1)).toPurchaseReadDto(purchaseMilk);
        verify(purchaseReadMapper, times(1)).toPurchaseReadDto(purchaseBread);

    }


}