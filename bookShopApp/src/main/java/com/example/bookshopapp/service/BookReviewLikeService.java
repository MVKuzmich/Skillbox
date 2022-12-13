package com.example.bookshopapp.service;

import com.example.bookshopapp.data.book.review.BookReviewLikeEntity;
import com.example.bookshopapp.repository.BookReviewLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class BookReviewLikeService {

    private BookReviewLikeRepository bookReviewLikeRepository;

    public BookReviewLikeService(BookReviewLikeRepository bookReviewLikeRepository) {
        this.bookReviewLikeRepository = bookReviewLikeRepository;
    }

    public void saveBookReviewLikeOrDislike(BookReviewLikeEntity bookReviewLikeEntity) {
        bookReviewLikeRepository.saveAndFlush(bookReviewLikeEntity);
    }
}
