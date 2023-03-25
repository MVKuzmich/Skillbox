package com.example.simpleshop.rest;

import com.example.simpleshop.dto.ReviewCreateDto;
import com.example.simpleshop.dto.ReviewReadDto;
import com.example.simpleshop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewRestController {

    public final ReviewService reviewService;

    @PostMapping
    public ReviewReadDto addReview(@RequestBody ReviewCreateDto reviewCreateDto,
                                   Principal principal) {
        return reviewService.addReview(reviewCreateDto, principal);
    }
}
