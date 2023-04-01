package com.example.simpleshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/*
- Информация о скидке состоит из:
	- Задействованных товаров;
	- Объема скидки;
	- Длительности скидки.

 */
@Entity
@Data
@ToString(exclude = "discountProducts")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private Integer discountValue;

    @Column(name = "duration")
    private Integer discountDuration;

    @OneToMany(mappedBy = "discount")
    private List<Product> discountProducts;

    public Discount(Integer discountValue, Integer discountDuration) {
        this.discountValue = discountValue;
        this.discountDuration = discountDuration;
    }
}
