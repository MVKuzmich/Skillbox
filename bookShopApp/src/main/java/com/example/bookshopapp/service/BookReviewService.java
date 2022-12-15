package com.example.bookshopapp.service;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.book.review.BookReviewEntity;
import com.example.bookshopapp.dto.BookReviewDto;
import com.example.bookshopapp.repository.BookRepository;
import com.example.bookshopapp.repository.BookReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookReviewService {

    private BookReviewRepository bookReviewRepository;
    private BookRepository bookRepository;

    public BookReviewService(BookReviewRepository bookReviewRepository, BookRepository bookRepository) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookRepository = bookRepository;
    }

    public void saveBookReview(BookReviewEntity bookReviewEntity) {
        bookReviewRepository.saveAndFlush(bookReviewEntity);
    }

    public BookReviewEntity getReviewById(Integer reviewId) {
        return bookReviewRepository.findById(reviewId).get();
    }

    public List<BookReviewDto> getBookReviewList(String slug) {
        Book book = bookRepository.findBySlug(slug);
        return book.getBookReviewSet().stream()
                .map(reviewEntity -> new BookReviewDto(
                        reviewEntity.getId(),
                        reviewEntity.getUser().getName(),
                        (int)Arrays.stream(reviewEntity.calcBookReviewRating()).filter(word -> word.equals("star")).count(),
                        reviewEntity.calcBookReviewRating(),
                        reviewEntity.getTime(),
                        reviewEntity.getText(),
                        reviewEntity.countLikes(),
                        reviewEntity.countDislikes()
                ))
                .sorted(Comparator.comparing(BookReviewDto::getRating).thenComparing(BookReviewDto::getCountLikes).reversed())
                .collect(Collectors.toList());
    }
}
