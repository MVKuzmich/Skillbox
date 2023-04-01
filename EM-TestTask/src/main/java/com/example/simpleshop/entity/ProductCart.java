package com.example.simpleshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = "productList")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createDate;

    private LocalDateTime paidDate;


    @OneToMany(mappedBy = "productCart", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<CartItem> productList = new ArrayList<>();




}
