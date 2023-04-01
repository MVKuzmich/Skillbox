package com.example.simpleshop.unit.service;

import com.example.simpleshop.dto.*;
import com.example.simpleshop.entity.*;
import com.example.simpleshop.enums.Role;
import com.example.simpleshop.mapper.RateReadMapper;
import com.example.simpleshop.repository.RateRepository;
import com.example.simpleshop.repository.UserRepository;
import com.example.simpleshop.service.RateService;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateServiceTest {


    @InjectMocks
    private RateService rateService;
    @Mock
    private RateRepository rateRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RateReadMapper rateReadMapper;

    @Mock
    Principal principal;


    User user;
    Product bread;
    Product milk;
    Company bakery;
    Company milkFactory;
    Purchase purchaseBread;
    Purchase purchaseMilk;
    Rate rateEntity;
    RateReadDto rateReadDto;
    RateCreateDto rateCreateDto;



    @BeforeEach
    void init() {
        rateCreateDto = new RateCreateDto(1L, 4);
        bakery = getCompany(1L, "Bakery");
        milkFactory = getCompany(2L, "Milk Factory");

        bread = getProduct("Bread", 1L, BigDecimal.valueOf(10L), bakery);

        milk = getProduct("Milk", 2L, BigDecimal.valueOf(20L), milkFactory);

        user = getUser();

        purchaseBread = getPurchase(1L, bread, 2);

        purchaseMilk = getPurchase(2L, milk, 5);
        user.getPurchaseList().addAll(List.of(purchaseBread, purchaseMilk));
        rateEntity = getRateEntity();
        user.getUserRates().add(rateEntity);
        rateReadDto = RateReadDto.builder()
        .id(1L)
                .userReadDto(new UserMinInfoDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()))
                .productReadDto(ProductMinInfoReadDto.builder()
                        .id(bread.getId())
                        .name(bread.getName())
                        .companyReadDto(new CompanyReadDto(bread.getCompany().getId(), bread.getCompany().getName()))
                        .discountReadDto(new DiscountReadDto(1L, 0, 0))
                        .build())
                .rate(rateEntity.getValue())
                .createDate(rateEntity.getCreateDate())
                .build();
    }

    @Test
    void checkAddRateMethod() {
        doReturn("test@gmail.com").when(principal).getName();
        doReturn(Optional.ofNullable(user)).when(userRepository).findByEmail("test@gmail.com");
        doReturn(rateEntity).when(rateRepository).save(any(Rate.class));
        doReturn(rateReadDto).when(rateReadMapper).toRateReadDto(any(Rate.class));

        RateReadDto actualRateReadDto = rateService.addRate(rateCreateDto, principal);

        assertThat(actualRateReadDto).isEqualTo(rateReadDto);

        verify(principal, times(1)).getName();
        verify(rateRepository, times(1)).save(any(Rate.class));
        verify(rateReadMapper, times(1)).toRateReadDto(rateEntity);

    }


    private Rate getRateEntity() {
        return Rate.builder()
                .id(1L)
                .user(user)
                .product(bread)
                .value(4)
                .createDate(LocalDateTime.of(2022, 12, 12, 12, 12))
                .build();
    }


    private User getUser() {
        return User.builder()
                .id(1L)
                .username("test")
                .email("test@gmail.com")
                .password("123")
                .balance(BigDecimal.ZERO)
                .role(Role.USER)
                .productCart(null)
                .build();
    }

    private Purchase getPurchase(long id, Product product, int productAmount) {
        return Purchase.builder()
                .id(id)
                .user(user)
                .product(product)
                .productAmount(productAmount)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(productAmount)))
                .delivery(null)
                .build();
    }

    private Company getCompany(long id, String companyName) {
        return Company.builder()
                .id(id)
                .name(companyName)
                .description("description")
                .logo("logo")
                .build();
    }

    private Product getProduct(String name, long id, BigDecimal price, Company company) {
        return Product.builder()
                .id(id)
                .name(name)
                .description("text")
                .company(bakery)
                .price(price)
                .quantityInStore(50L)
                .discount(new Discount(1L, 0, 0, null))
                .keyWords("text")
                .characteristics("text")
                .build();
    }


}