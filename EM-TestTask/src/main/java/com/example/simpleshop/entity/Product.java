package com.example.simpleshop.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"productRates", "productReviews", "cartItemList"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


    private BigDecimal price;

    @Column(name = "quantity_in_store")
    private Long quantityInStore;

    @ManyToOne(fetch = FetchType.LAZY)
    private Discount discount;

    @Column(name = "key_words")
    private String keyWords;

    private String characteristics;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Rate> productRates = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Review> productReviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CartItem> cartItemList = new ArrayList<>();




}
