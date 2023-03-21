package com.example.simpleshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = "purchaseList")
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(mappedBy = "purchaseCart")
    private Delivery delivery;

    @OneToMany(mappedBy = "purchaseCart", cascade = CascadeType.ALL)
    private List<Purchase> purchaseList = new ArrayList<>();




}
