package com.example.bookshopapp.repository;

import com.example.bookshopapp.data.book.review.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewLikeRepository extends JpaRepository<BookReviewLikeEntity, Integer> {

}
