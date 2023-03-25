package com.example.simpleshop.service;

import com.example.simpleshop.dto.ReviewCreateDto;
import com.example.simpleshop.dto.ReviewReadDto;
import com.example.simpleshop.entity.Purchase;
import com.example.simpleshop.entity.Review;
import com.example.simpleshop.entity.User;
import com.example.simpleshop.exception.ProductNotBuyException;
import com.example.simpleshop.mapper.ReviewReadMapper;
import com.example.simpleshop.repository.ReviewRepository;
import com.example.simpleshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewReadMapper reviewReadMapper;

    @Transactional
    public ReviewReadDto addReview(ReviewCreateDto reviewCreateDto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        return user.getPurchaseList().stream()
                .map(Purchase::getProduct)
                .filter(product -> product.getId().equals(reviewCreateDto.getProductId()))
                .findFirst()
                .map(product -> new Review(user, product, reviewCreateDto.getDescription()))
                .map(reviewRepository::save)
                .map(reviewReadMapper::toReviewReadDto)
                .orElseThrow(() -> new ProductNotBuyException("You can't add review! Buy product"));
    }
}
