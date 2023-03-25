package com.example.simpleshop.repository;

import com.example.simpleshop.entity.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {

}
