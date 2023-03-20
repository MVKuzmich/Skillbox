package com.example.simpleshop.repository;

import com.example.simpleshop.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("select d from Discount d where d.discountValue = :value and d.discountDuration = :duration")
    Optional<Discount> findByValueAndDuration(Integer value, Integer duration);
}
