package com.example.simpleshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String description;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public Review(User user, Product product, String description) {
        this.user = user;
        this.product = product;
        this.description = description;
        this.createDate = LocalDateTime.now();
    }
}
