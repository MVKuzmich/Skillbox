package com.example.simpleshop.service;

import com.example.simpleshop.dto.RateCreateDto;
import com.example.simpleshop.dto.RateReadDto;
import com.example.simpleshop.entity.Purchase;
import com.example.simpleshop.entity.Rate;
import com.example.simpleshop.entity.User;
import com.example.simpleshop.exception.ProductNotBuyException;
import com.example.simpleshop.mapper.RateReadMapper;
import com.example.simpleshop.repository.RateRepository;
import com.example.simpleshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.OAEPParameterSpec;
import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final UserRepository userRepository;
    private final RateReadMapper rateReadMapper;

    @Transactional
    public RateReadDto addRate(RateCreateDto rateCreateDto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        return user.getPurchaseList().stream()
                .map(Purchase::getProduct)
                .filter(product -> product.getId().equals(rateCreateDto.getProductId()))
                .findFirst()
                .map(product -> new Rate(user, product, rateCreateDto.getRate(), LocalDateTime.now()))
                .map(rateRepository::save)
                .map(rateReadMapper::toRateReadDto)
                .orElseThrow(() -> new ProductNotBuyException("You can't rate product! Buy it!"));
    }

    public RateReadDto findById(Long rateId) {
        return rateRepository.findById(rateId)
                .map(rateReadMapper::toRateReadDto)
                .orElseThrow();
    }
}

