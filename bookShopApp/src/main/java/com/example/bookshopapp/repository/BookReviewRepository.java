package com.example.bookshopapp.repository;

import com.example.bookshopapp.data.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Integer> {

}
