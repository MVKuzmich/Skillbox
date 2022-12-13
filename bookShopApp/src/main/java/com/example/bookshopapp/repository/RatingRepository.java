package com.example.bookshopapp.repository;

import com.example.bookshopapp.data.bookrate.BookRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RatingRepository extends JpaRepository<BookRateEntity, Integer> {


}
