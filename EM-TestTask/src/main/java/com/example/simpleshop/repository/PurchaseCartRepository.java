package com.example.simpleshop.repository;


import com.example.simpleshop.entity.PurchaseCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PurchaseCartRepository extends JpaRepository<PurchaseCart, Long> {

    @Query(value = "select count (c.id) from purchase_cart c where c.delivery_id is null and c.user_id = :id", nativeQuery = true)
    Integer countUserCartWithNullDelivery(Long id);

}
