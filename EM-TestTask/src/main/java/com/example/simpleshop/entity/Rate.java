package com.example.simpleshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rates")
@Builder
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private Integer value;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public Rate(User user, Product product, Integer value, LocalDateTime createDate) {
        this.user = user;
        this.product = product;
        this.value = value;
        this.createDate = createDate;
    }
}
