package com.example.simpleshop.integration.service;

import com.example.simpleshop.dto.*;
import com.example.simpleshop.entity.Rate;
import com.example.simpleshop.integration.IntegrationTestBase;
import com.example.simpleshop.mapper.RateReadMapper;
import com.example.simpleshop.repository.RateRepository;
import com.example.simpleshop.repository.UserRepository;
import com.example.simpleshop.service.RateService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = SimpleShopApplication.class,
//        initializers = ConfigDataApplicationContextInitializer.class)

@RequiredArgsConstructor
@Transactional
class RateServiceIT extends IntegrationTestBase {

    @Autowired
    private RateService rateService;

    @Autowired
    private RateReadMapper rateReadMapper;

    @Test
    void addRateMethodIT() {
        RateCreateDto rateCreateDto = new RateCreateDto(1L, 4);
        RateReadDto actualRateReadDto = rateService.addRate(rateCreateDto, () -> "alice@gmail.com");
        RateReadDto expected = rateService.findById(21L);

        assertThat(actualRateReadDto).isEqualTo(expected);
    }

    @Test
    void findByIdIT() {

        RateReadDto expected = RateReadDto.builder()
                .id(1L)
                .userReadDto(new UserMinInfoDto(5L, "Era", "era@gmail.com"))
                .productReadDto(ProductMinInfoReadDto.builder()
                        .id(10L)
                        .name("Temp")
                        .companyReadDto(new CompanyReadDto(2L, "Shuffledrive"))
                        .discountReadDto(new DiscountReadDto(4L, 25, 10))
                        .build())
                .rate(2)
                .createDate(LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0))
                .build();

        assertThat(rateService.findById(1L)).isEqualTo(expected);

    }
}
