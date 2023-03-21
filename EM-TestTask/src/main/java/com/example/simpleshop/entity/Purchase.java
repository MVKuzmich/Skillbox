package com.example.simpleshop.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_cart_id", referencedColumnName = "id")
    private PurchaseCart purchaseCart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(name = "product_amount")
    private Integer productAmount;

    @Column(name = "total_price")
    private BigDecimal totalPrice;


}
