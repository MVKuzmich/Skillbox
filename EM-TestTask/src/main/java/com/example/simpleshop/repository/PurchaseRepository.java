package com.example.simpleshop.repository;

import com.example.simpleshop.dto.PurchaseReadDto;
import com.example.simpleshop.entity.Purchase;
import com.example.simpleshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Page<Purchase> findByUser(User user, Pageable pageable);

    @Query("select p from Purchase p where p.user.id = :userId")
    Page<Purchase> findAllByUserId(Long userId, Pageable pageable);
}
