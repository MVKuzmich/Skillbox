package com.example.simpleshop.service;

import com.example.simpleshop.dto.PurchaseReadDto;
import com.example.simpleshop.entity.User;
import com.example.simpleshop.mapper.PurchaseListMapper;
import com.example.simpleshop.mapper.PurchaseReadMapper;
import com.example.simpleshop.repository.PurchaseRepository;
import com.example.simpleshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseReadMapper purchaseReadMapper;
    private final UserRepository userRepository;

    public Page<PurchaseReadDto> findAll(Principal principal, Pageable pageable) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        return purchaseRepository.findByUser(user, pageable)
                .map(purchaseReadMapper::toPurchaseReadDto);
    }

    public Page<PurchaseReadDto> findAllByUserId(Long userId, Pageable pageable) {
        return purchaseRepository.findAllByUserId(userId, pageable)
                .map(purchaseReadMapper::toPurchaseReadDto);

    }
}
