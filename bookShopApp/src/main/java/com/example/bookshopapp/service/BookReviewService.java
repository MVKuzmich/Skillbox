package com.example.bookshopapp.service;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.book.review.BookReviewEntity;
import com.example.bookshopapp.repository.BookReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewService {

    private BookReviewRepository bookReviewRepository;

    public BookReviewService(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    public void saveBookReview(BookReviewEntity bookReviewEntity) {
        bookReviewRepository.saveAndFlush(bookReviewEntity);
    }

    public BookReviewEntity getReviewById(Integer reviewId) {
        return bookReviewRepository.findById(reviewId).get();
    }
}
