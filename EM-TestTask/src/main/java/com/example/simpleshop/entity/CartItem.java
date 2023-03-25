package com.example.simpleshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private ProductCart productCart;

    private Integer productAmount;

    private BigDecimal totalPrice;

    public CartItem(Product product, ProductCart productCart, Integer productAmount, BigDecimal totalPrice) {
        this.product = product;
        this.productCart = productCart;
        this.productAmount = productAmount;
        this.totalPrice = totalPrice;
    }
}
