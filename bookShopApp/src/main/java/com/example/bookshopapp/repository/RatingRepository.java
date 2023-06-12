package com.example.bookshopapp.repository;

import com.example.bookshopapp.data.bookrate.BookRateEntity;
import com.example.bookshopapp.dto.RateRangeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RatingRepository extends JpaRepository<BookRateEntity, Integer> {

    @Query("select br.value as rateValue, count(br.value) as rateCount " +
            "from BookRateEntity br " +
            "join br.book b " +
            "where b.slug = :bookSlug " +
            "group by br.value")
    List<RateRangeDto> findRateRangeForBook(String bookSlug);


}
